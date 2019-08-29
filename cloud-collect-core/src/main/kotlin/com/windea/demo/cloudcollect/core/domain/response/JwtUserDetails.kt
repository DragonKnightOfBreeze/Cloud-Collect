package com.windea.demo.cloudcollect.core.domain.response

import com.windea.demo.cloudcollect.core.domain.entity.User
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*

/**Jwt用户详情。用于安全验证。*/
data class JwtUserDetails(
	val delegateUser: User
) : UserDetails {
	override fun getUsername(): String {
		return delegateUser.username
	}
	
	override fun getPassword(): String {
		return delegateUser.password
	}
	
	override fun getAuthorities(): Collection<GrantedAuthority> {
		return AuthorityUtils.createAuthorityList(delegateUser.role.toString())
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
		return delegateUser.isActivated
	}
}

