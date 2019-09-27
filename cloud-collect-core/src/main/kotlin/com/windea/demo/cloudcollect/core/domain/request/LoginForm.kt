package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import io.swagger.annotations.*
import java.io.*
import javax.validation.constraints.*

@ApiModel("用户名&密码登录表单。 ")
@Domain
class LoginForm(
	@ApiModelProperty("用户名。")
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}")
	@field:Username(message = "{validation.User.username.Username}")
	val username: String,
	
	@ApiModelProperty("密码。")
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@field:Password(message = "{validation.User.password.Password}")
	val password: String
) : Serializable
