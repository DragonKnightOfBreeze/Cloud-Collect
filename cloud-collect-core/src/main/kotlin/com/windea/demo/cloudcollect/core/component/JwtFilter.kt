package com.windea.demo.cloudcollect.core.component

import com.windea.demo.cloudcollect.core.service.impl.*
import mu.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.web.authentication.*
import org.springframework.stereotype.*
import org.springframework.web.filter.*
import javax.servlet.*
import javax.servlet.http.*

/**Jwt过滤器。*/
@Component
class JwtFilter(
	private val jwtProvider: JwtProvider,
	private val userDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
		val token = jwtProvider.getToken(request) ?: return
		val username = jwtProvider.getUsername(token) ?: return
		
		val userDetails = userDetailsService.loadUserByUsername(username)
		if(jwtProvider.validateToken(token, userDetails)) {
			val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
			authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
			SecurityContextHolder.getContext().authentication = authentication
			logger.info("Set authentication from Jwt success. Authenticated user: $username")
		} else {
			logger.warn("Set authentication from Jwt failed.")
		}
		
		chain.doFilter(request, response)
	}
	
	
	companion object : KLogging()
}
