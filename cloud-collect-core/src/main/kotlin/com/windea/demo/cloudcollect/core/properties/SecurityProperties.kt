package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*

/**安全校验的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.security")
data class SecurityProperties(
	val secret: String,
	val clientId: String,
	val resourceId: String
)
