package com.windea.demo.cloudcollect.core.component

import mu.*
import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.security.web.*
import org.springframework.stereotype.*
import javax.servlet.http.*

/**Jwt安全验证进入点。*/
@Component
class JwtEntryPoint : AuthenticationEntryPoint {
	override fun commence(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
		logger.error("Unauthorized error. Message: ${e.message}")
	}
	
	
	companion object : KLogging()
}
