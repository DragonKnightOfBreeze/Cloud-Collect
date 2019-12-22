package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*

/**安全校验的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.jwt")
data class JwtProperties(
	val tokenHeader: String,
	val tokenHead: String,
	val secret: String,
	val expiration: Int
)
