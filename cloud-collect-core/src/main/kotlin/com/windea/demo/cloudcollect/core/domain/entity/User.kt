package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**用户。*/
@Entity
@EntityListeners(AuditingEntityListener::class)
class User(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**用户名。*/
	@Column(unique = true, nullable = false, length = 16)
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	var username: String,
	
	/**密码。这里存储的是加密后的密码，可以进行参数验证，不要限制长度。*/
	@JsonIgnore
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	var password: String,
	
	/**邮箱。*/
	@Column(unique = true, nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Default::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [Default::class])
	var email: String,
	
	/**昵称。*/
	@Column(nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Default::class])
	var nickname: String,
	
	/**简介。*/
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.User.introduce.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.User.introduce.Size}", groups = [Default::class])
	var introduce: String = "这家伙很懒，什么也没留下。",
	
	/**头像地址。*/
	@Column(length = 512)
	var avatarUrl: String = "",
	
	/**背景地址。*/
	@Column(length = 512)
	var backgroundUrl: String = "",
	
	/**身份。*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var role: Role = Role.NORMAL,
	
	/**是否已激活。*/
	@Column
	var activateStatus: Boolean = false
) : Serializable {
	/**注册时间。*/
	@Column
	@CreatedDate
	lateinit var registerTime: LocalDateTime
	
	/**资料更新时间。*/
	@Column
	@LastModifiedDate
	lateinit var updateTime: LocalDateTime
	
	/**激活码。*/
	@JsonIgnore
	@Column
	var activateCode: String? = null
	
	/**重置密码验证码。*/
	@JsonIgnore
	@Column
	var resetPasswordCode: String? = null
	
	/**用户的关注用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	val followToUserList: MutableList<User> = mutableListOf()
	
	/**该用户的粉丝用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "followToUserList")
	val followByUserList: MutableList<User> = mutableListOf()
	
	/**该用户点赞的收藏列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	val praiseToCollectList: MutableList<Collect> = mutableListOf()
	
	
	override fun equals(other: Any?) = other is User && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
