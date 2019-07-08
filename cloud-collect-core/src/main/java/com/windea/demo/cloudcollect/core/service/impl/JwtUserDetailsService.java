package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Jwt用户详情的服务。用于安全验证。
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final String message = "401 用户未找到！";

	private final UserRepository repository;

	public JwtUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) {
		var user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(message));
		return new JwtUserDetails(user);
	}
}
