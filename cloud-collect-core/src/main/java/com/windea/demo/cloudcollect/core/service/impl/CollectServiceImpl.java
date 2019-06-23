package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl implements CollectService {
	private final CollectRepository repository;

	public CollectServiceImpl(CollectRepository repository) {
		this.repository = repository;
	}
}
