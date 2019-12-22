package com.windea.demo.cloudcollect.core.configuration

import com.windea.demo.cloudcollect.core.component.*
import com.windea.demo.cloudcollect.core.service.impl.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.web.authentication.*
import javax.sql.*

//更新到最新版本，使用更适合Kotlin的Dsl式配置。
//实现Jwt的自动配置，但仍然需要显式注册过滤器和进入点。

/**Spring Security的配置类。*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启全局安全验证注解
class WebSecurityConfiguration(
	private val dataSource: DataSource,
	private val userDetailsService: UserDetailsServiceImpl,
	private val jwtFilter: JwtFilter,
	private val jwtEntryPoint: JwtEntryPoint
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
		}.sessionManagement {
			//因为使用jwt，所以http session可以是无状态的
			it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		}.headers {
			//禁用缓存
			it.cacheControl().disable()
		}.exceptionHandling {
			it.authenticationEntryPoint(jwtEntryPoint)
		}.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
	}
	
	@Bean
	override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
	
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
	
	//@Bean
	//fun persistentTokenRepository(): PersistentTokenRepository = JdbcTokenRepositoryImpl().also {
	//	it.dataSource = dataSource
	//	//创建persistent_logins表，之后需要注释掉
	//	//it.setCreateTableOnStartup(true)
	//}
}
