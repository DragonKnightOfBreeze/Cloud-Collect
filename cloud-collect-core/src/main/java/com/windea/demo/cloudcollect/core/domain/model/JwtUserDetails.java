package com.windea.demo.cloudcollect.core.domain.model;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jwt用户详情（用于安全验证）。
 */
public class JwtUserDetails implements UserDetails {
	private static final long serialVersionUID = -8990133011806369007L;

	private final User delegateUser;

	public JwtUserDetails(User delegateUser) {
		this.delegateUser = delegateUser;
	}


	public User getDelegateUser() {
		return delegateUser;
	}

	@Override
	public String getUsername() {
		return delegateUser.getUsername();
	}

	@Override
	public String getPassword() {
		return delegateUser.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(delegateUser.getRole()).stream()
			.map(e -> new SimpleGrantedAuthority(e.toString()))
			.collect(Collectors.toSet());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return delegateUser.getActivated();
	}
}

