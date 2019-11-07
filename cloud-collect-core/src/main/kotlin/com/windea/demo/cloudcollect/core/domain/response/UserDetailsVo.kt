package com.windea.demo.cloudcollect.core.domain.response

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.domain.entity.User
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*

/**Jwt用户详情。用于安全验证。*/
@Domain
class UserDetailsVo(
	val delegateUser: User
) : UserDetails {
	
	override fun getUsername(): String {
		return delegateUser.username
	}
	
	override fun getPassword(): String {
		return delegateUser.password
	}
	
	override fun getAuthorities(): Collection<GrantedAuthority> {
		//NOTE 这里使用name而非toString，因为后者可能为中文
		return AuthorityUtils.createAuthorityList(delegateUser.role.name)
	}
	
	override fun isAccountNonExpired(): Boolean {
		return true
	}
	
	override fun isAccountNonLocked(): Boolean {
		return true
	}
	
	override fun isCredentialsNonExpired(): Boolean {
		return true
	}
	
	override fun isEnabled(): Boolean {
		return delegateUser.activateStatus
	}
}

