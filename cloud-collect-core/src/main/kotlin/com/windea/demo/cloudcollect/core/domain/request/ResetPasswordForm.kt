package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.validation.annotation.*
import java.io.*
import javax.validation.constraints.*

/**重置密码视图。*/
data class ResetPasswordForm(
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}")
	@field:Username(message = "{validation.User.username.ValidUsername}")
	val username: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@field:Password(message = "{validation.User.password.ValidPassword}")
	val password: String
) : Serializable
