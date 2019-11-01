package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.GlobalConfig.dateFormat
import com.windea.demo.cloudcollect.core.GlobalConfig.requireActivation
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

@ApiModel("用户。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueUser(groups = [Create::class])
data class User(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	@ApiModelProperty("用户名。")
	@get:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Username(message = "{validation.User.username.Username}", groups = [Create::class, Modify::class])
	@Column(unique = true, nullable = false, length = 16)
	val username: String,
	
	@ApiModelProperty("密码。这里存储的是加密后的密码，可以进行参数验证，不要限制数据库中对应字段的长度。")
	@get:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Password(message = "{validation.User.password.Password}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
	var password: String,
	
	@ApiModelProperty("邮箱。")
	@get:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Email(message = "{validation.User.email.Email}", groups = [Create::class, Modify::class])
	@Column(unique = true, nullable = false, length = 64)
	val email: String,
	
	@ApiModelProperty("昵称。")
	@get:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [Modify::class])
	@get:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Modify::class])
	@Column(nullable = false, length = 64)
	var nickname: String = username,
	
	@ApiModelProperty("简介。")
	@Column(nullable = false)
	@get:NotEmpty(message = "{validation.User.introduce.NotEmpty}", groups = [Modify::class])
	@get:Size(min = 1, max = 255, message = "{validation.User.introduce.Size}", groups = [Modify::class])
	var introduce: String = "这家伙很懒，什么也没留下。",
	
	@ApiModelProperty("头像地址。")
	@Column(length = 512, nullable = false)
	var avatarUrl: String = "",
	
	@ApiModelProperty("背景地址。")
	@Column(length = 512, nullable = false)
	var backgroundUrl: String = "",
	
	@ApiModelProperty("身份。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val role: Role = Role.NORMAL
) : Serializable {
	//当配置为不要求你激活时，直接激活对应的用户
	@ApiModelProperty("是否已激活。")
	@Column(nullable = false)
	var activateStatus: Boolean = !requireActivation
	
	@ApiModelProperty("注册时间。")
	@Column
	@CreatedDate
	@JsonFormat(pattern = dateFormat)
	var registerTime: LocalDateTime? = null
	
	@ApiModelProperty("资料更新时间。")
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = dateFormat)
	var updateTime: LocalDateTime? = null
	
	@ApiModelProperty("用户的关注用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany
	val followToUserList: MutableList<User> = mutableListOf()
	
	@ApiModelProperty("该用户的粉丝用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany(mappedBy = "followToUserList")
	val followByUserList: MutableList<User> = mutableListOf()
	
	@ApiModelProperty("该用户点赞的收藏列表。懒加载。")
	@JsonIgnore
	@ManyToMany
	val praiseToCollectList: MutableList<Collect> = mutableListOf()
	
	@ApiModelProperty("关注用户数量。")
	@Transient
	var followToUserCount: Long = 0
	
	@ApiModelProperty("粉丝用户数量。")
	@Transient
	var followByUserCount: Long = 0
	
	@ApiModelProperty("收藏数量。")
	@Transient
	var collectCount: Long = 0
	
	@ApiModelProperty("评论数量。")
	@Transient
	var commentCount: Long = 0
	
	@ApiModelProperty("通知数量。")
	@Transient
	var noticeCount: Long = 0
	
	override fun equals(other: Any?) = other === this || (other is User && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
