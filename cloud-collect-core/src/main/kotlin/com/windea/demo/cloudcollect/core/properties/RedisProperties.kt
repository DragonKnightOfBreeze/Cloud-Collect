package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import java.time.*

/**Redis缓存的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.redis")
data class RedisProperties(
	val activateCodePrefix: String,
	val resetPasswordCodePrefix: String,
	val expiration: Duration
)
