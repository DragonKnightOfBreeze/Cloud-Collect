package com.windea.demo.cloudcollect.core.domain.view

import com.windea.demo.cloudcollect.core.validation.annotation.*
import javax.validation.constraints.*

/**用户名&密码登录视图。 */
data class UsernamePasswordLoginView(
	@NotEmpty(message = "{validation.User.username.NotEmpty}")
	@Username(message = "{validation.User.username.ValidUsername}")
	val username: String,
	
	@NotEmpty(message = "{validation.User.password.NotEmpty}")
	@Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : LoginView
