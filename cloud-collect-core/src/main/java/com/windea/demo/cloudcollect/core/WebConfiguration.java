package com.windea.demo.cloudcollect.core;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;

/**
 * Web模块的配置类。
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Bean
	public MessageSource messageSource() {
		// 在web环境中一定要定位到classpath 否则默认到当前web应用下找
		var messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public Validator validator() {
		//如果不配置，默认使用classpath下的ValidationMessages.properties
		var factory = new LocalValidatorFactoryBean();
		factory.setProviderClass(HibernateValidator.class);
		factory.setValidationMessageSource(messageSource());
		return factory;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//添加跨域请求映射。默认为空。
		registry.addMapping("/**")
			.allowedOrigins("*").allowedMethods("*").allowCredentials(false)
			.maxAge(3600);
	}
}
