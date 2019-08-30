package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*

import javax.validation.constraints.*

/**邮箱注册视图。 */
@UniqueEmailRegisterForm
data class EmailRegisterForm(
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	val username: String,
	
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	val password: String,
	
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Default::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [Default::class])
	val email: String,
	
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Default::class])
	val nickname: String
) : RegisterForm
