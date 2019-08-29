package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.validation.annotation.*

import javax.validation.constraints.*

/**邮箱注册视图。 */
data class EmailRegisterForm(
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}")
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}")
	val nickname: String,
	
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}")
	@field:Username(message = "{validation.User.username.ValidUsername}")
	val username: String,
	
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}")
	@field:Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@field:Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : RegisterForm
