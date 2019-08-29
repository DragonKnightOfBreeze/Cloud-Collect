package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**全局配置的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.config")
class ConfigProperties {
	/**注册用户时是否需要邮箱验证。*/
	var requireActivate: Boolean = true
	
	/**注册、登录等操作时是否发送邮件。*/
	var sendEmail: Boolean = true
}
