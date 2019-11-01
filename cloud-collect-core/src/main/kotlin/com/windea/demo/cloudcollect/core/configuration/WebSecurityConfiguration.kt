package com.windea.demo.cloudcollect.core.configuration

import com.windea.demo.cloudcollect.core.service.impl.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.bcrypt.*

//TODO 更新到最新版本，使用更适合Kotlin的Dsl式配置。
//DONE 实现Jwt的自动配置，但仍然需要显式注册过滤器和进入点。

/**Spring Security的配置类。*/
@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
	private val userDetailsService: UserDetailsServiceImpl
) : WebSecurityConfigurerAdapter() {
	
	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
	}
	
	override fun configure(http: HttpSecurity) {
		http.authorizeRequests {
			//配置路径权限规则
			it.antMatchers("/admin/**").hasRole("ADMIN")
			it.anyRequest().permitAll()
		}.csrf {
			//禁用csrf（跨站点请求伪造）防护
			it.disable()
		}.headers {
			//禁用缓存
			it.cacheControl().disable()
		}
	}
	
	@Bean
	override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
	
	//用于加密密码。
	@Bean
	fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
	
	//@Bean
	//fun accessTokenConverter(): JwtAccessTokenConverter = JwtAccessTokenConverter()
	//
	//@Bean
	//fun tokenStore(): TokenStore {
	//	return JwtTokenStore(accessTokenConverter())
	//}
	//
	//@Bean
	//@Primary //防止重名的意外
	//fun tokenServices(): ResourceServerTokenServices = DefaultTokenServices().apply {
	//	setTokenStore(tokenStore())
	//	setSupportRefreshToken(true)
	//}
}
