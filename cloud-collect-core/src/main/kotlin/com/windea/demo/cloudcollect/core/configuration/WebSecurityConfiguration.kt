package com.windea.demo.cloudcollect.core.configuration

import com.windea.demo.cloudcollect.core.service.impl.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.oauth2.provider.token.*
import org.springframework.security.oauth2.provider.token.store.*

//TODO 更新到最新版本，使用更适合Kotlin的Dsl式配置。
//DONE 实现Jwt的自动配置，但仍然需要显式注册过滤器和进入点。

/**Spring Security的配置类。*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
	private val userDetailsService: UserDetailsServiceImpl
) : WebSecurityConfigurerAdapter() {
	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
	}
	
	override fun configure(http: HttpSecurity) {
		//配置路径权限规则
		http.authorizeRequests()
			.antMatchers(
				"/collectCategory/**",
				"/collectTag/**",
				"/comment/**",
				"/notice/**",
				"/user/**"
			).authenticated()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
			.and()
			//禁用csrf（跨站点请求伪造）防护
			.cors().disable()
			//不启用会话
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//禁用缓存
			.headers().cacheControl()
	}
	
	@Bean
	override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
	
	//用于加密密码。
	@Bean
	fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
	
	@Bean
	fun accessTokenConverter(): JwtAccessTokenConverter = JwtAccessTokenConverter()
	
	@Bean
	fun tokenStore(): TokenStore {
		return JwtTokenStore(accessTokenConverter())
	}
	
	@Bean
	@Primary //防止重名的意外
	fun tokenServices(): ResourceServerTokenServices = DefaultTokenServices().apply {
		setTokenStore(tokenStore())
		setSupportRefreshToken(true)
	}
}
