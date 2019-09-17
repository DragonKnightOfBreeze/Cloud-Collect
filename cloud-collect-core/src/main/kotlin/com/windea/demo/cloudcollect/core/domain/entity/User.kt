package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

@ApiModel("用户。")
@Entity
@EntityListeners(AuditingEntityListener::class)
class User(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("用户名。")
	@Column(unique = true, nullable = false, length = 16)
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	var username: String,
	
	@ApiModelProperty("密码。这里存储的是加密后的密码，可以进行参数验证，不要限制数据库中对应字段的长度。")
	@JsonIgnore
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	var password: String,
	
	@ApiModelProperty("邮箱。")
	@Column(unique = true, nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Default::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [Default::class])
	var email: String,
	
	@ApiModelProperty("昵称。")
	@Column(nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Default::class])
	var nickname: String,
	
	@ApiModelProperty("简介。")
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.User.introduce.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.User.introduce.Size}", groups = [Default::class])
	var introduce: String = "这家伙很懒，什么也没留下。",
	
	@ApiModelProperty("头像地址。")
	@Column(length = 512)
	var avatarUrl: String = "",
	
	@ApiModelProperty("背景地址。")
	@Column(length = 512)
	var backgroundUrl: String = "",
	
	@ApiModelProperty("身份。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var role: Role = Role.NORMAL,
	
	@ApiModelProperty("是否已激活。")
	@Column
	var activateStatus: Boolean = false
) : Serializable {
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
	
	@ApiModelProperty("注册时间。")
	@Column
	@CreatedDate
	lateinit var registerTime: LocalDateTime
	
	@ApiModelProperty("资料更新时间。")
	@Column
	@LastModifiedDate
	lateinit var updateTime: LocalDateTime
	
	@ApiModelProperty("用户的关注用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	val followToUserList: MutableList<User> = mutableListOf()
	
	@ApiModelProperty("该用户的粉丝用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "followToUserList")
	val followByUserList: MutableList<User> = mutableListOf()
	
	@ApiModelProperty("该用户点赞的收藏列表。懒加载。")
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	val praiseToCollectList: MutableList<Collect> = mutableListOf()
	
	
	override fun equals(other: Any?) = other is User && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
