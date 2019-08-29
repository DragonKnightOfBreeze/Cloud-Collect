package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.validation.annotation.*
import javax.validation.constraints.*

/**邮箱&密码登录视图。 */
data class EmailPasswordLoginForm(
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}")
	@field:Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@field:Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : LoginForm
