package com.windea.demo.cloudcollect.core.component;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * Jwt提供器。
 */
@Component
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${com.windea.security.jwtSecret}")
	private String jwtSecret;
	@Value("${com.windea.security.jwtExpiration}")
	private int jwtExpiration;


	/**
	 * 生成令牌。
	 */
	public String generateToken(Authentication authentication) {
		return Jwts.builder()
			.setSubject(((UserDetails) authentication.getPrincipal()).getUsername())
			.setIssuedAt(new Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	/**
	 * 刷新令牌。
	 */
	public String refreshToken(String token) {
		if(!isTokenNotExpired(token)) {
			return token;
		}
		return Jwts.builder()
			.setClaims(getClaims(token))
			.setIssuedAt(new Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	/**
	 * 生成过期时间。
	 */
	private Date generateExpiration() {
		return new Date(System.currentTimeMillis() + jwtExpiration * 1000);
	}

	/**
	 * 得到要求。
	 */
	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		} catch(Exception e) {
			logger.error("Invalid Jwt format.");
			return Jwts.claims();
		}
	}

	/**
	 * 得到用户名。
	 */
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * 验证令牌。
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		return isUsernameValid(token, userDetails) && isTokenNotExpired(token);
	}

	/**
	 * 判断用户名是否合法。
	 */
	private boolean isUsernameValid(String token, UserDetails userDetails) {
		var username = getUsername(token);
		return username != null && Objects.equals(getUsername(token), userDetails.getUsername());
	}

	/**
	 * 判断令牌是否已过期。
	 */
	private boolean isTokenNotExpired(String token) {
		var expiration = getClaims(token).getExpiration();
		return expiration != null && expiration.after(new Date());
	}
}
