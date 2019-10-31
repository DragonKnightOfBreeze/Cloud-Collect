package com.windea.demo.cloudcollect.core.configuration

import com.windea.demo.cloudcollect.core.properties.*
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.oauth2.config.annotation.web.configuration.*
import org.springframework.security.oauth2.config.annotation.web.configurers.*
import org.springframework.security.oauth2.provider.token.*

/**OAuth2资源服务器的配置。*/
@Configuration
@EnableResourceServer
class ResourceServerConfiguration(
	private val tokenServices: ResourceServerTokenServices,
	private val securityProperties: SecurityProperties
) : ResourceServerConfigurerAdapter() {
	override fun configure(resources: ResourceServerSecurityConfigurer) {
		resources.resourceId(securityProperties.resourceId).tokenServices(tokenServices)
	}
	
	override fun configure(http: HttpSecurity) {
		http.requestMatchers()
			.antMatchers("/oauth/**")
			.and()
			.authorizeRequests()
			.antMatchers(
				"/collect/**",
				"/category/**",
				"/tag/**",
				"/comment/**",
				"/createAndSendToAll/**",
				"/user/**"
			).authenticated()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
	}
}
