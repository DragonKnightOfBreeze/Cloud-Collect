package com.windea.demo.cloudcollect.core.configuration;

import com.windea.demo.cloudcollect.core.component.JwtEntryPoint;
import com.windea.demo.cloudcollect.core.component.JwtFilter;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.service.impl.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的配置类。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final JwtFilter jwtFilter;
	private final JwtEntryPoint jwtEntryPoint;
	private final JwtUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final MutableAclService mutableAclService;

	public JwtSecurityConfiguration(JwtFilter jwtFilter, JwtEntryPoint jwtEntryPoint,
		JwtUserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
		MutableAclService mutableAclService) {
		this.jwtFilter = jwtFilter;
		this.jwtEntryPoint = jwtEntryPoint;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.mutableAclService = mutableAclService;
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		//禁用csrf（跨站点请求伪造）防护
		httpSecurity.csrf().disable()
			//配置路径权限规则
			.authorizeRequests()
			.antMatchers(
				"/collect/**",
				"/collectCategory/**",
				"/collectTag/**",
				"/comment/**",
				"/create/**",
				"/user/**").authenticated()
			.antMatchers("/admin/**").hasRole(Role.ADMIN.toString())
			.anyRequest().permitAll()
			.and()
			//不启用会话
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//添加过滤器和错误处理器
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
			.and()
			//禁用缓存
			.headers().cacheControl();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Bean
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setPermissionEvaluator(aclPermissionEvaluator());
		return handler;
	}

	@Bean
	public AclPermissionEvaluator aclPermissionEvaluator() {
		return new AclPermissionEvaluator(mutableAclService);
	}
}
