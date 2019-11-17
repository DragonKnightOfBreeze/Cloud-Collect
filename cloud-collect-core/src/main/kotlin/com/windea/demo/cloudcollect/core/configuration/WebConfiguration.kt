package com.windea.demo.cloudcollect.core.configuration

import org.hibernate.validator.*
import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.validation.*
import org.springframework.validation.beanvalidation.*
import org.springframework.web.servlet.config.annotation.*

/**Web的配置类。*/
@Configuration
class WebConfiguration : WebMvcConfigurer {
	//添加跨域请求映射。默认为空。
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**").allowedMethods("*").allowCredentials(false).maxAge(3600)
	}
	
	//使用SpringBoot时，converter, formatter, filter, interceptor等类型的bean会自动注册，不需要重载对应的方法。
	//SpringBoot已经帮我们配置好了妥善的messageSource，不需要手动进行配置，否则会出错。
	
	@Bean
	fun validator(messageSource: MessageSource): Validator = LocalValidatorFactoryBean().apply {
		setProviderClass(HibernateValidator::class.java)
		setValidationMessageSource(messageSource)
	}
}
