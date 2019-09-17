package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import javax.validation.constraints.*

@ApiModel("邮箱&密码登录表单。")
@Model
data class EmailPasswordLoginForm(
	@ApiModelProperty("邮箱。")
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Default::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [Default::class])
	val email: String,
	
	@ApiModelProperty("密码。")
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	val password: String
) : LoginForm
