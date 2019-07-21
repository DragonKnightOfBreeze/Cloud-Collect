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

/**用户。*/
@Entity
@UniqueUser
data class User(
	/**用户名。*/
	@Column(unique = true, nullable = false, length = 16)
	@NotEmpty(message = "{validation.User.username.NotEmpty}")
	@Username(message = "{validation.User.username.ValidUsername}")
	var username: String = "",
	
	/**邮箱。*/
	@Column(unique = true, nullable = false, length = 64)
	@NotEmpty(message = "{validation.User.email.NotEmpty}")
	@Email(message = "{validation.User.email.Email}")
	var email: String = "",
	
	/**密码。这里存储的是加密后的密码，可以进行参数验证，不能限制长度。*/
	@Column(nullable = false)
	@NotEmpty(message = "{validation.User.password.NotEmpty}")
	@Password(message = "{validation.User.password.ValidPassword}")
	var password: String = "",
	
	/**昵称。*/
	@Column(nullable = false, length = 64)
	@NotEmpty(message = "{validation.User.nickname.NotEmpty}")
	@Size(min = 1, max = 64, message = "{validation.User.nickname.Size}")
	var nickname: String = "",
	
	/**简介。*/
	@Column(nullable = false)
	@NotEmpty(message = "{validation.User.introduce.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.User.introduce.Size}")
	var introduce: String = "这家伙很懒，什么也没留下。",
	
	/**头像地址。*/
	@Column(length = 512)
	var avatarUrl: String = "",
	
	/**背景地址。*/
	@Column(length = 512)
	var backgroundUrl: String = ""
) : Serializable {
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
	
	/**身份。*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var role: Role = Role.NORMAL
	
	/**TODO 是否已激活。暂时设为总是已激活。*/
	@Column
	var isActivated: Boolean = true
	
	/**注册时间。*/
	@Column
	@CreatedDate
	var registerTime: LocalDateTime? = null
	
	/**资料更新时间。*/
	@Column
	@LastModifiedDate
	var updateTime: LocalDateTime? = null
	
	/**用户的关注用户列表。懒加载。*/
	@ManyToMany(cascade = [CascadeType.MERGE])
	@JsonIgnore
	var followToUserList: MutableList<User> = mutableListOf()
	
	/**该用户的粉丝用户列表。懒加载。*/
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "followToUserList")
	@JsonIgnore
	var followByUserList: MutableList<User> = mutableListOf()
	
	/**该用户点赞的收藏列表。懒加载。*/
	@ManyToMany(cascade = [CascadeType.MERGE])
	@JsonIgnore
	var praiseToCollectList: MutableList<Collect> = mutableListOf()
}
