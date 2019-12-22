package com.windea.demo.cloudcollect.core.configuration

import org.hibernate.validator.*
import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.http.converter.*
import org.springframework.validation.*
import org.springframework.validation.beanvalidation.*
import org.springframework.web.servlet.config.annotation.*


/**Web的配置类。*/
@Configuration
@EnableWebMvc
class WebConfiguration(
	private val messageSource: MessageSource
) : WebMvcConfigurer {
	//添加资源管理器，用于处理静态文件
	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
		registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/CloudCollect/upload/")
		registry.addResourceHandler("/export/**").addResourceLocations("file:D:/CloudCollect/export/")
	}
	
	//添加跨域请求映射。默认为空。
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true).maxAge(3600 * 24)
	}
	
	//override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
	//	converters+=stringHttpMessageConverter()
	//}
	
	//使用SpringBoot时，converter, formatter, filter, interceptor等类型的bean会自动注册，不需要重载对应的方法。
	//SpringBoot已经帮我们配置好了妥善的messageSource，不需要手动进行配置，否则会出错。
	//对于validator bean，一定要注上@Primary，因为可能存在多个。
	//这里的返回值类型不能随意修改
	
	@Bean
	@Primary
	fun stringHttpMessageConverter(): StringHttpMessageConverter = StringHttpMessageConverter(Charsets.UTF_8)
	
	@Bean
	@Primary
	fun validator(messageSource: MessageSource): Validator = LocalValidatorFactoryBean().also {
		it.setProviderClass(HibernateValidator::class.java)
		it.setValidationMessageSource(messageSource)
	}
}
