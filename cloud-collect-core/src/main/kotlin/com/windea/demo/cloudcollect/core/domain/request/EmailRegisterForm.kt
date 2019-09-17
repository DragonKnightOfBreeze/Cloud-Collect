package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*

import javax.validation.constraints.*

@ApiModel("邮箱注册表单。")
@Model
@UniqueEmailRegisterForm
data class EmailRegisterForm(
	@ApiModelProperty("用户名。")
	@field:NotEmpty(message = "{validation.User.username.NotEmpty}", groups = [Default::class])
	@field:Username(message = "{validation.User.username.ValidUsername}", groups = [Default::class])
	val username: String,
	
	@ApiModelProperty("密码。")
	@field:NotEmpty(message = "{validation.User.password.NotEmpty}", groups = [Default::class])
	@field:Password(message = "{validation.User.password.ValidPassword}", groups = [Default::class])
	val password: String,
	
	@ApiModelProperty("邮箱。")
	@field:NotEmpty(message = "{validation.User.email.NotEmpty}", groups = [Default::class])
	@field:Email(message = "{validation.User.email.Email}", groups = [Default::class])
	val email: String,
	
	@ApiModelProperty("昵称。")
	@field:NotEmpty(message = "{validation.User.nickname.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 64, message = "{validation.User.nickname.Size}", groups = [Default::class])
	val nickname: String
) : RegisterForm
