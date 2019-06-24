package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CollectTagRepository;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import org.springframework.stereotype.Service;

@Service
public class CollectTagServiceImpl implements CollectTagService {
	private final CollectTagRepository repository;
	private final CollectRepository collectRepository;

	public CollectTagServiceImpl(CollectTagRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}
}
