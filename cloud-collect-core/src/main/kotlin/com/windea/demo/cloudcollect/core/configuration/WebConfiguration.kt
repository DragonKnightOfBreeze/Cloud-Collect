package com.windea.demo.cloudcollect.core.configuration

import org.hibernate.validator.*
import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import org.springframework.validation.beanvalidation.*
import org.springframework.web.servlet.config.annotation.*
import javax.validation.*

/** Web的配置类。*/
@Configuration
open class WebConfiguration : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		//添加跨域请求映射。默认为空。
		registry.addMapping("/**").allowedMethods("*").allowCredentials(false).maxAge(3600)
	}
	
	@Bean
	open fun messageSource(): MessageSource {
		// 在web环境中一定要定位到classpath，否则默认到当前web应用下找
		val messageSource = ReloadableResourceBundleMessageSource()
		messageSource.setBasename("classpath:messages")
		messageSource.setDefaultEncoding("UTF-8")
		return messageSource
	}
	
	@Bean
	open fun validator(): Validator {
		//如果不配置，默认使用classpath下的ValidationMessages.properties
		val factory = LocalValidatorFactoryBean()
		factory.setProviderClass(HibernateValidator::class.java)
		factory.setValidationMessageSource(messageSource())
		return factory
	}
	
	@Bean
	open fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}
