package com.windea.demo.cloudcollect.core.component;

import com.windea.demo.cloudcollect.core.service.impl.JwtUserDetailsService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt过滤器。
 */
@Component
@CommonsLog
public class JwtFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final JwtUserDetailsService userDetailsService;
	@Value("${com.windea.security.jwtTokenHeader}")
	private String tokenHeader;
	@Value("${com.windea.security.jwtTokenHead}")
	private String tokenHead;

	public JwtFilter(JwtProvider jwtProvider,
		JwtUserDetailsService userDetailsService) {
		this.jwtProvider = jwtProvider;
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	throws ServletException, IOException {
		var token = getToken(request);
		//如果存在令牌，则解析令牌，生成并存入认真对象
		if(StringUtils.hasText(token)) {
			var username = jwtProvider.getUsername(token);
			var userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtProvider.validateToken(token, userDetails)) {
				var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
				log.info("Set authentication from Jwt success. Authenticated user: " + username);
			} else {
				log.error("Set authentication from Jwt failed.");
			}
		}
		chain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String header = request.getHeader(this.tokenHeader);
		if(StringUtils.startsWithIgnoreCase(header, this.tokenHead)) {
			return header.substring(this.tokenHead.length() + 1);
		}
		return null;
	}
}
