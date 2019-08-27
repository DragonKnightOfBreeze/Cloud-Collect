package com.windea.demo.cloudcollect.core.domain.view

import com.windea.demo.cloudcollect.core.validation.annotation.*
import javax.validation.constraints.*

/**用户名&密码登录视图。 */
data class UsernamePasswordLoginView(
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}")
	@field:Username(message = "{validation.User.username.ValidUsername}")
	val username: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@field:Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : LoginView
