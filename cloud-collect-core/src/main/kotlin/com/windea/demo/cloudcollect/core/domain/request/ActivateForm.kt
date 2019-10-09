package com.windea.demo.cloudcollect.core.domain.request

import com.windea.demo.cloudcollect.core.annotation.*
import io.swagger.annotations.*

@ApiModel("激活表单。")
@Domain
class ActivateForm(
	@ApiModelProperty("用户名。")
	val username: String,
	
	@ApiModelProperty("激活码。")
	val activateCode: String
)
