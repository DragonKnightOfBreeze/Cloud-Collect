package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**参数验证的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.validation")
open class ValidationProperties {
	lateinit var username: String
	
	lateinit var password: String
}
