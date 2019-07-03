package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import com.windea.demo.cloudcollect.core.repository.CollectCategoryRepository;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CollectCategoryServiceImpl implements CollectCategoryService {
	private final CollectCategoryRepository repository;
	private final CollectRepository collectRepository;

	public CollectCategoryServiceImpl(CollectCategoryRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Override
	public void create(CollectCategory category) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void modify(Long id, CollectCategory category) {

	}

	@Override
	public CollectCategory get(Long id) {
		return null;
	}

	@Override
	public Page<CollectCategory> queryByUser(Long userId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<CollectCategory> queryByUserAndName(Long userId, String name, Pageable pageable) {
		return null;
	}

	@Override
	public boolean exists(Long userId, String name) {
		return false;
	}
}
