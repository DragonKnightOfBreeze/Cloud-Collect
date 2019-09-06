package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**参数验证的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.validation")
class ValidationProperties {
	/**用户名为6~16位的字母、数字和下划线，以字母开头。*/
	lateinit var username: String
	
	/**密码为6~16位的字母、数字和下划线。*/
	lateinit var password: String
}
