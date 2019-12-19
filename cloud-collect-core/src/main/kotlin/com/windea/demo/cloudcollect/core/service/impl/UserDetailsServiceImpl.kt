package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.User
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.repository.*
import org.springframework.cache.annotation.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

//用于安全验证
@Service
class UserDetailsServiceImpl(
	private val userRepository: UserRepository
) : UserDetailsService {
	@Cacheable(cacheNames = ["user"], key = "'/currentUser?username='+#username")
	override fun loadUserByUsername(username: String): UserDetails {
		//尝试按用户名查找，如果找不到，再尝试按邮箱查找
		return (userRepository.findByUsername(username) ?: userRepository.findByEmail(username))?.toUserDetails()
		       ?: throw UsernameNotFoundException(ResultStatus.USER_NOT_FOUND.message)
	}
	
	private fun User.toUserDetails() = UserDetailsVo(this)
}
