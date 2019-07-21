package com.windea.demo.cloudcollect.core.domain.view

import com.windea.demo.cloudcollect.core.validation.annotation.*

import javax.validation.constraints.*

/**邮箱注册视图。 */
data class EmailRegisterView(
	@NotEmpty(message = "{validation.User.nickname.NotEmpty}")
	@Size(min = 1, max = 64, message = "{validation.User.nickname.Size}")
	val nickname: String,
	
	@NotEmpty(message = "{validation.User.username.NotEmpty}")
	@Username(message = "{validation.User.username.ValidUsername}")
	val username: String,
	
	@NotEmpty(message = "{validation.User.email.NotEmpty}")
	@Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@NotEmpty(message = "{validation.User.password.NotEmpty}")
	@Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : RegisterView
