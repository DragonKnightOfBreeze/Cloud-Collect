package com.windea.demo.cloudcollect.demo.configuration

import com.windea.demo.cloudcollect.demo.component.*
import com.windea.demo.cloudcollect.demo.service.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.method.configuration.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.crypto.password.*

/**Spring Security的配置类。*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
	//private val jwtFilter: JwtFilter,
	private val jwtEntryPoint: JwtEntryPoint,
	private val userDetailsService: JwtUserDetailsService,
	private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {
	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
	}
	
	override fun configure(httpSecurity: HttpSecurity) {
		//禁用csrf（跨站点请求伪造）防护
		httpSecurity.csrf().disable()
			//配置路径权限规则
			.authorizeRequests()
			.antMatchers(
				"/collect/**",
				"/collectCategory/**",
				"/collectTag/**",
				"/comment/**",
				"/createAndSendToAll/**",
				"/user/**"
			).authenticated()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
			.and()
			//不启用会话
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//添加过滤器和错误处理器
			//.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
			.and()
			//禁用缓存
			.headers().cacheControl()
	}
	
	//需要重载方法以提取bean。
	@Bean
	override fun authenticationManagerBean(): AuthenticationManager {
		return super.authenticationManagerBean()
	}
}
