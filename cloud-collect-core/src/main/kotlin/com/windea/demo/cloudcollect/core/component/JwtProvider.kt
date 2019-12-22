package com.windea.demo.cloudcollect.core.component

import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.service.impl.*
import io.jsonwebtoken.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import java.util.*
import javax.servlet.http.*

@Component
class JwtProvider(
	private val userDetailsService: UserDetailsServiceImpl,
	private val jwtProperties: JwtProperties
) {
	/**从http请求中得到令牌。*/
	fun getToken(request: HttpServletRequest): String? {
		val header = request.getHeader(jwtProperties.tokenHeader)
		return if(header != null && header.startsWith(jwtProperties.tokenHead, true)) {
			header.replace(jwtProperties.tokenHead, "").trim().takeIf { it.isNotEmpty() }
		} else null
	}
	
	/**生成令牌。*/
	fun generateToken(username: String): String {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
			.compact()
	}
	
	/**刷新令牌。*/
	fun refreshToken(token: String): String {
		return Jwts.builder()
			.setClaims(getClaims(token))
			.setIssuedAt(Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
			.compact()
	}
	
	private fun generateExpiration(): Date {
		return Date(System.currentTimeMillis() + jwtProperties.expiration * 1000)
	}
	
	/**得到要求。*/
	private fun getClaims(token: String): Claims {
		return try {
			Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body
		} catch(e: Exception) {
			println("Invalid Jwt format.")
			Jwts.claims()
		}
	}
	
	/**验证令牌。*/
	fun validateToken(token: String): UserDetails? {
		val claims = getClaims(token)
		
		//判断令牌是否过期
		val expiration = claims.expiration ?: return null
		if(expiration < Date()) return null
		
		//判断用户是否合法
		val username = claims.subject ?: return null
		return userDetailsService.loadUserByUsername(username)
	}
}
