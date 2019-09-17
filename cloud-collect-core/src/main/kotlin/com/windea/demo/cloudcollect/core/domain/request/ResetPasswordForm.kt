package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import java.io.*
import javax.validation.constraints.*

@ApiModel("重置密码表单。")
@Model
data class ResetPasswordForm(
	@ApiModelProperty("用户名。")
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	val username: String,
	
	@ApiModelProperty("密码。")
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	val password: String
) : Serializable
