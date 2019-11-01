package com.windea.demo.cloudcollect.core.configuration

import org.hibernate.validator.*
import org.springframework.context.annotation.*
import org.springframework.context.support.*
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
	
	//在web环境中一定要定位到classpath，否则默认到当前web应用下找。
	@Bean
	fun messageSource(): ReloadableResourceBundleMessageSource = ReloadableResourceBundleMessageSource().apply {
		setBasename("classpath:messages")
		setDefaultEncoding("UTF-8")
	}
	
	//需要明确配置验证器，指定需要使用的资源文件。
	@Bean
	fun validator(): Validator = LocalValidatorFactoryBean().apply {
		setProviderClass(HibernateValidator::class.java)
		setValidationMessageSource(messageSource())
	}
	
}
