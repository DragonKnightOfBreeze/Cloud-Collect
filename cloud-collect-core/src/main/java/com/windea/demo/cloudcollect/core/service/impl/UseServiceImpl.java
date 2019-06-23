package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.UserRepository;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UseServiceImpl implements UserService {
	private final UserRepository repository;

	public UseServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
}
