package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

/**Jwt用户详情的服务。用于安全验证。*/
@Service
open class JwtUserDetailsService(
	private val repository: UserRepository
) : UserDetailsService {
	override fun loadUserByUsername(username: String): UserDetails {
		val user = repository.findByUsername(username).orElseThrow { UserNotFoundException() }
		return JwtUserDetails(user)
	}
}
