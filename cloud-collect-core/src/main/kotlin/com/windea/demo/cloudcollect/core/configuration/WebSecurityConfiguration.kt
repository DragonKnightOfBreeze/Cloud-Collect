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
import org.springframework.security.crypto.password.*
import org.springframework.security.web.access.expression.*
import org.springframework.security.web.authentication.*

/**Spring Security的配置类。*/
//DONE 实现Jwt的自动配置，但仍然需要显式注册过滤器和进入点。
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
	private val jwtFilter: JwtFilter,
	private val jwtEntryPoint: JwtEntryPoint,
	private val userDetailsService: JwtUserDetailsService,
	private val passwordEncoder: PasswordEncoder,
	private val permissionEvaluator: PropertyBasedPermissionEvaluator
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
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
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
	
	//使用自定义的访问权限鉴别器，以便使用注解进行访问权限控制。
	//NOTE 需要配置spring.main.allow-bean-definition-overriding=true
	@Bean
	open fun webSecurityExpressionHandler(): DefaultWebSecurityExpressionHandler {
		return DefaultWebSecurityExpressionHandler().apply {
			setPermissionEvaluator(permissionEvaluator)
		}
	}
}
