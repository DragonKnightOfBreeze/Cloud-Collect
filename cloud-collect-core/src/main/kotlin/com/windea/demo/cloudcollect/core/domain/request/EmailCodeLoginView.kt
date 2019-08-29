package com.windea.demo.cloudcollect.core.domain.request

import javax.validation.constraints.*

/**邮箱&验证码登录视图。 */
data class EmailCodeLoginView(
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}")
	@field:Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@field:NotEmpty(message = "{validation.User.code.NotEmpty}")
	@field:Size(min = 6, max = 6, message = "{validation.User.code.Size}")
	val code: String
) : LoginView
