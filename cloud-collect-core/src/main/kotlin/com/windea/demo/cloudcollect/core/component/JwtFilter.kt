package com.windea.demo.cloudcollect.core.component

import com.windea.demo.cloudcollect.core.service.impl.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.web.authentication.*
import org.springframework.stereotype.*
import org.springframework.web.filter.*
import javax.servlet.*
import javax.servlet.http.*

@Component
class JwtFilter(
	private val jwtProvider: JwtProvider,
	private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
		//NOTE 不要直接返回
		val token = jwtProvider.getToken(request)
		
		if(token != null) {
			val userDetails = jwtProvider.validateToken(token)
			if(userDetails != null) {
				val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
				authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
				SecurityContextHolder.getContext().authentication = authentication
				logger.info("Set authentication from Jwt success. Authenticated user: ${userDetails.username}")
			} else {
				logger.warn("Set authentication from Jwt failed.")
			}
		} else {
			logger.info("No token, ignored.")
		}
		
		chain.doFilter(request, response)
	}
}
