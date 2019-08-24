package com.windea.demo.cloudcollect.core.component

import io.jsonwebtoken.*
import mu.*
import org.springframework.beans.factory.annotation.*
import org.springframework.security.core.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import java.util.*
import javax.servlet.http.*

/**Jwt提供器。*/
@Component
class JwtProvider {
	@Value("\${com.windea.security.tokenHeader}")
	private lateinit var tokenHeader: String
	@Value("\${com.windea.security.tokenHead}")
	private lateinit var tokenHead: String
	@Value("\${com.windea.security.secret}")
	private lateinit var secret: String
	@Value("\${com.windea.security.expiration}")
	private var expiration: Int = 0
	
	
	/**从http请求中得到令牌。*/
	fun getToken(request: HttpServletRequest): String? {
		val header = request.getHeader(this.tokenHeader)
		return if(header?.startsWith(this.tokenHeader, true) == true) {
			header.substring(this.tokenHead.length + 1).takeIf { it.isNotBlank() }
		} else null
	}
	
	/**生成令牌。*/
	fun generateToken(authentication: Authentication): String {
		return Jwts.builder()
			.setSubject((authentication.principal as UserDetails).username)
			.setIssuedAt(Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact()
	}
	
	/**刷新令牌。*/
	fun refreshToken(token: String): String {
		return if(!isTokenNotExpired(token)) {
			token
		} else {
			Jwts.builder()
				.setClaims(getClaims(token))
				.setIssuedAt(Date())
				.setExpiration(generateExpiration())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact()
		}
	}
	
	/**生成过期时间。*/
	private fun generateExpiration(): Date {
		return Date(System.currentTimeMillis() + expiration * 1000)
	}
	
	/**得到要求。*/
	private fun getClaims(token: String): Claims {
		return try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
		} catch(e: Exception) {
			logger.error("Invalid Jwt format.")
			Jwts.claims()
		}
	}
	
	/**得到用户名。*/
	fun getUsername(token: String): String? {
		return getClaims(token).subject
	}
	
	/**验证令牌。*/
	fun validateToken(token: String, userDetails: UserDetails): Boolean {
		return isUsernameValid(token, userDetails) && isTokenNotExpired(token)
	}
	
	/**判断用户名是否合法。*/
	private fun isUsernameValid(token: String, userDetails: UserDetails): Boolean {
		val username = getUsername(token)
		return username == userDetails.username
	}
	
	/**判断令牌是否已过期。*/
	private fun isTokenNotExpired(token: String): Boolean {
		val expiration = getClaims(token).expiration
		return expiration?.after(Date()) == true
	}
	
	
	companion object : KLogging()
}
