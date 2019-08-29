package com.windea.demo.cloudcollect.demo.service

import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

/**Jwt用户详情的服务。用于安全验证。*/
@Service
open class JwtUserDetailsService : UserDetailsService {
	override fun loadUserByUsername(username: String): UserDetails {
		return User("Windea", "BreezesLanding", AuthorityUtils.createAuthorityList("ADMIN"))
	}
}
