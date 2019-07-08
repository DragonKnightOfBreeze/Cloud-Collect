package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.exception.NotFoundException;
import com.windea.demo.cloudcollect.core.repository.CollectCategoryRepository;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CollectCategoryServiceImpl implements CollectCategoryService {
	private final CollectCategoryRepository repository;
	private final CollectRepository collectRepository;

	public CollectCategoryServiceImpl(CollectCategoryRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Transactional
	@Override
	public void create(CollectCategory category, User user) {
		category.setUser(user);
		repository.save(category);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public void modify(Long id, CollectCategory category) {
		var rawCategory = repository.getOne(id);
		rawCategory.setName(category.getName());
		rawCategory.setSummary(category.getSummary());
		repository.save(rawCategory);
	}

	@Cacheable("collectCategory")
	@Override
	public CollectCategory get(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Cacheable("collectCategory.collectPage")
	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.findByCategory_IdAndDeleteStatusFalse(id, pageable);
	}

	@Cacheable("collectCategory.collectCount")
	@Override
	public Long getCollectCount(Long id) {
		return collectRepository.countByCategory_IdAndDeleteStatusFalse(id);
	}

	@Cacheable("collectCategoryPage")
	@Override
	public Page<CollectCategory> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("collectCategoryPage.byUser")
	@Override
	public Page<CollectCategory> findByUser(Long userId, Pageable pageable) {
		return repository.findByUser_Id(userId, pageable);
	}

	@Cacheable("collectCategoryPage.byUserAndName")
	@Override
	public Page<CollectCategory> findByUserAndName(Long userId, String name, Pageable pageable) {
		return repository.findByUser_IdAndNameContains(userId, name, pageable);
	}

	@Override
	public boolean exists(CollectCategory category) {
		var userId = category.getUser().getId();
		var name = category.getName();
		return repository.existsByUser_IdAndName(userId, name);
	}
}
