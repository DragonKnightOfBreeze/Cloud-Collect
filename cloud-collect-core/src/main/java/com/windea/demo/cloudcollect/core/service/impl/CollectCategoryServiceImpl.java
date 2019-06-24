package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.CollectCategoryRepository;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import org.springframework.stereotype.Service;

@Service
public class CollectCategoryServiceImpl implements CollectCategoryService {
	private final CollectCategoryRepository repository;
	private final CollectRepository collectRepository;

	public CollectCategoryServiceImpl(CollectCategoryRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}
}
