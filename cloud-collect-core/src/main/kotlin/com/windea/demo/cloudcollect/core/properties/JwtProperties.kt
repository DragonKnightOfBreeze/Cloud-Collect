package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**Jwt的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.security.jwt")
class JwtProperties {
	lateinit var signingKey: String
	
	lateinit var verifierKey: String
	
	lateinit var secret: String
	
	lateinit var clientId: String
	
	lateinit var resourceId: String
	
	var grantType: String = "password"
}
