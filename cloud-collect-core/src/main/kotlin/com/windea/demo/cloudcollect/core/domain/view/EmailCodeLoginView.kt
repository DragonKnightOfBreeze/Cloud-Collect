package com.windea.demo.cloudcollect.core.domain.view

import javax.validation.constraints.*

/**邮箱&验证码登录视图。 */
data class EmailCodeLoginView(
	@NotEmpty(message = "{validation.User.email.NotEmpty}")
	@Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@NotEmpty(message = "{validation.User.code.NotEmpty}")
	@Size(min = 6, max = 6, message = "{validation.User.code.Size}")
	val code: String
) : LoginView
