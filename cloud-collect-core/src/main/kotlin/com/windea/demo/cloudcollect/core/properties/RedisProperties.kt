package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*
import java.time.*

/**Redis缓存的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.redis")
class RedisProperties {
	lateinit var activateCodePrefix: String
	lateinit var resetPasswordPrefix: String
	lateinit var expiration: Duration
}
