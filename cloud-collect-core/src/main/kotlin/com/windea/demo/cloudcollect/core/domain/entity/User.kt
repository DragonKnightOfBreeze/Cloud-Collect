package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/** 用户。*/
@UniqueUser
@Entity
class User(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/** 用户名。*/
	@NotEmpty(message = "{validation.User.username.NotEmpty}")
	@Username(message = "{validation.User.username.ValidUsername}")
	@Column(unique = true, nullable = false, length = 16)
	var username: String = "",
	
	/** 邮箱。*/
	@NotEmpty(message = "{validation.User.email.NotEmpty}")
	@Email(message = "{validation.User.email.Email}")
	@Column(unique = true, nullable = false, length = 64)
	var email: String = "",
	
	/**密码。这里存储的是加密后的密码，可以进行参数验证，不能限制长度。*/
	@NotEmpty(message = "{validation.User.password.NotEmpty}")
	@Password(message = "{validation.User.password.ValidPassword}")
	@Column(nullable = false)
	var password: String = "",
	
	/** 昵称。*/
	@NotEmpty(message = "{validation.User.nickname.NotEmpty}")
	@Size(min = 1, max = 64, message = "{validation.User.nickname.Size}")
	@Column(nullable = false, length = 64)
	var nickname: String = "",
	
	/** 简介。*/
	@NotEmpty(message = "{validation.User.introduce.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.User.introduce.Size}")
	@Column(nullable = false)
	var introduce: String = "这家伙很懒，什么也没留下。",
	
	/** 头像地址。*/
	@Column(length = 512)
	var avatarUrl: String = "",
	
	/** 背景地址。*/
	@Column(length = 512)
	var backgroundUrl: String = "",
	
	/** 身份。*/
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	var role: Role = Role.NORMAL,
	
	/** TODO 激活状态。暂时设为总是已激活。*/
	@Column
	var activateStatus: Boolean = true,
	
	/** 注册时间。*/
	@CreatedDate
	@Column
	var registerTime: LocalDateTime? = null,
	
	/** 资料更新时间。*/
	@LastModifiedDate
	@Column
	var updateTime: LocalDateTime? = null,
	
	/** 该用户关注的用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	var followToUserList: MutableList<User> = mutableListOf(),
	
	/** 关注该用户的用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "followToUserList")
	var followByUserList: MutableList<User> = mutableListOf(),
	
	/** 该用户点赞的收藏列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE])
	var praiseToCollectList: MutableList<Collect> = mutableListOf()
) : Serializable
