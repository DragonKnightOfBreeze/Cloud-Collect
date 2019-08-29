package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**Jwt的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.security.jwt")
open class JwtProperties {
	lateinit var tokenHeader: String
	
	lateinit var tokenHead: String
	
	lateinit var secret: String
	
	lateinit var expiration: String
}
