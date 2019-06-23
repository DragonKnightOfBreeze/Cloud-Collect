package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.FollowRepository;
import com.windea.demo.cloudcollect.core.service.FollowService;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
	private final FollowRepository repository;

	public FollowServiceImpl(FollowRepository repository) {
		this.repository = repository;
	}
}
