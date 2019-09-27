package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.User
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.repository.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

/**Jwt用户详情的服务。用于安全验证。*/
@Service
class UserDetailsServiceImpl(
	private val userRepository: UserRepository
) : UserDetailsService {
	override fun loadUserByUsername(username: String): UserDetails {
		return userRepository.findByUsername(username)?.toUserDetails() ?: throw UserNotFoundException()
	}
	
	private fun User.toUserDetails() = UserDetailsVo(this)
	
	//登录方法向SecurityContext中存储的是validAuthentication，而非authentication
	//前者不包含密码，但包含权限信息，因此不需要实现UserDetailsPasswordService
}
