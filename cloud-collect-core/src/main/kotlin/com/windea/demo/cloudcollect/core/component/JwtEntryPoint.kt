package com.windea.demo.cloudcollect.core.component

import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.security.web.*
import org.springframework.stereotype.*
import javax.servlet.http.*

@Component
class JwtEntryPoint : AuthenticationEntryPoint {
	override fun commence(request: HttpServletRequest, response: HttpServletResponse,
		authException: AuthenticationException) {
		println("Unauthorized error. ${authException.message}")
		response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
	}
}
