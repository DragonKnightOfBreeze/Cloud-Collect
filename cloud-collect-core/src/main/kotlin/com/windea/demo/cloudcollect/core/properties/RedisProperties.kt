package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*
import java.time.*

/**Redis缓存的属性类。*/
@Component
@ConfigurationProperties("cloud-collect.redis")
class RedisProperties {
	lateinit var activateCodePrefix: String
	lateinit var resetPasswordCodePrefix: String
	lateinit var expiration: Duration
}
