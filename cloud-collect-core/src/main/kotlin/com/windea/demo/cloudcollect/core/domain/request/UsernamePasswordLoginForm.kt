package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import javax.validation.constraints.*

@ApiModel("用户名&密码登录表单。 ")
data class UsernamePasswordLoginForm(
	@ApiModelProperty("用户名。")
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	val username: String,
	
	@ApiModelProperty("密码。")
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	val password: String
) : LoginForm
