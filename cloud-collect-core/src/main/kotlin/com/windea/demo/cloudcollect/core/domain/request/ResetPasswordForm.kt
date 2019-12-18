package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import io.swagger.annotations.*
import java.io.*
import javax.validation.constraints.*

@ApiModel("重置密码表单。")
@Domain
data class ResetPasswordForm(
	val username: String,
	
	@ApiModelProperty("密码。")
	@get:NotEmpty(message = "{validation.User.password.NotEmpty}")
	@get:Password(message = "{validation.User.password.Password}")
	val password: String
) : Serializable
