package com.windea.demo.cloudcollect.demo.domain

import com.windea.demo.cloudcollect.demo.validation.*
import com.windea.demo.cloudcollect.demo.validation.annotation.*
import javax.persistence.*

import javax.validation.constraints.*

/**邮箱注册视图。 */
@Entity
class EmailRegisterForm(
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [GroupA::class])
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Default::class])
	var nickname: String,
	
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [GroupA::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [GroupA::class])
	var username: String,
	
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [GroupA::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [GroupA::class])
	var email: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [GroupA::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [GroupA::class])
	var password: String,
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
)
