package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.exception.NotFoundException;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CollectTagRepository;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CollectTagServiceImpl implements CollectTagService {
	private final CollectTagRepository repository;
	private final CollectRepository collectRepository;

	public CollectTagServiceImpl(CollectTagRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Transactional
	@Override
	public void create(CollectTag tag, User user) {
		tag.setUser(user);
		repository.save(tag);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public void modify(Long id, CollectTag tag) {
		var rawTag = repository.getOne(id);
		rawTag.setName(tag.getName());
		rawTag.setSummary(tag.getSummary());
		repository.save(rawTag);
	}

	@Cacheable("collectTag")
	@Override
	public CollectTag get(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Cacheable("collectTag.collectPage")
	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.findByTag_IdAndDeletedFalse(id, pageable);
	}

	@Cacheable("collectTag.collectCount")
	@Override
	public Long getCollectCount(Long id) {
		return collectRepository.countByTag_IdAndDeletedFalse(id);
	}

	@Cacheable("collectTagPage")
	@Override
	public Page<CollectTag> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("collectTagPage.byUser")
	@Override
	public Page<CollectTag> findByUser(Long userId, Pageable pageable) {
		return repository.findByUser_Id(userId, pageable);
	}

	@Cacheable("collectTagPage.byUserAndName")
	@Override
	public Page<CollectTag> findByUserAndName(Long userId, String name, Pageable pageable) {
		return repository.findByUser_IdAndNameContains(userId, name, pageable);
	}

	@Override
	public boolean exists(CollectTag tag) {
		var userId = tag.getUser().getId();
		var name = tag.getName();
		return repository.existsByUser_IdAndName(userId, name);
	}
}
