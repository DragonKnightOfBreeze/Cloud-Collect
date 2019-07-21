package com.windea.demo.cloudcollect.core.configuration

import org.hibernate.validator.*
import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*
import org.springframework.context.support.*
import org.springframework.orm.hibernate5.support.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.validation.*
import org.springframework.validation.beanvalidation.*
import org.springframework.web.context.request.*
import org.springframework.web.servlet.config.annotation.*

/**Web的配置类。*/
@Configuration
@EnableConfigurationProperties
open class WebConfiguration : WebMvcConfigurer {
	//添加跨域请求映射。默认为空。
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**").allowedMethods("*").allowCredentials(false).maxAge(3600)
	}
	
	//使用SpringBoot时，converter, formatter, filter, interceptor等类型的bean会自动注册，不需要重载对应的方法。
	
	//在web环境中一定要定位到classpath，否则默认到当前web应用下找。
	@Bean
	open fun messageSource(): ReloadableResourceBundleMessageSource {
		return ReloadableResourceBundleMessageSource().also {
			it.setBasename("classpath:messages")
			it.setDefaultEncoding("UTF-8")
		}
	}
	
	//需要明确配置验证器，指定需要使用的资源文件。
	@Bean
	open fun validator(): Validator {
		return LocalValidatorFactoryBean().also {
			it.setProviderClass(HibernateValidator::class.java)
			it.setValidationMessageSource(messageSource())
		}
	}
	
	//用于加密密码。
	@Bean
	open fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}
	
	//用于在每次请求时确保打开hibernate session，以处理懒加载问题。
	@Bean
	open fun openSessionInViewInterceptor(): WebRequestInterceptor {
		return OpenSessionInViewInterceptor()
	}
}
