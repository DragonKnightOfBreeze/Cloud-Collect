package com.windea.demo.cloudcollect.core.domain.view

import com.windea.demo.cloudcollect.core.validation.annotation.*
import javax.validation.constraints.*

/**邮箱&密码登录视图。 */
data class EmailPasswordLoginView(
	@NotEmpty(message = "{validation.User.email.NotEmpty}")
	@Email(message = "{validation.User.email.Email}")
	val email: String,
	
	@NotEmpty(message = "{validation.User.password.NotEmpty}")
	@Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : LoginView
