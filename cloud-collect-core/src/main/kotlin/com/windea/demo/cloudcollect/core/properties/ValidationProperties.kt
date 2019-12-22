package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*

/**参数验证的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.validation")
data class ValidationProperties(
	//6~16位的字母、数字和下划线，以字母开头
	val username: String,
	//6~16位的字母、数字和下划线
	val password: String
)
