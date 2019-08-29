package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**Jwt的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.security.jwt")
class JwtProperties {
	/**jwt口令的请求头。*/
	lateinit var tokenHeader: String
	
	/**jwt口令的开头。*/
	lateinit var tokenHead: String
	
	/**jwt密钥。*/
	lateinit var secret: String
	
	/**jwt过期时间。*/
	var expiration: Int = 86400
}
