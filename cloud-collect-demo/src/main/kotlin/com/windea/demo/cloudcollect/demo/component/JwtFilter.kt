package com.windea.demo.cloudcollect.demo.component

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
open class JwtFilter(
	private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
		//NOTE 不要直接返回！！！
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
		}
		
		chain.doFilter(request, response)
	}
	
	
	companion object : KLogging()
}
