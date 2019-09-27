package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

/**安全校验的属性类。*/
@Component
@ConfigurationProperties("cloud-collect.security")
class SecurityProperties {
	lateinit var secret: String
	lateinit var clientId: String
	lateinit var resourceId: String
}
