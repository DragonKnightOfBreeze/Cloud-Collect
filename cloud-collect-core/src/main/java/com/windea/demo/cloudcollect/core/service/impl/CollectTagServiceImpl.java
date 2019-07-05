package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CollectTagRepository;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CollectTagServiceImpl implements CollectTagService {
	private final CollectTagRepository repository;
	private final CollectRepository collectRepository;

	public CollectTagServiceImpl(CollectTagRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Override
	public void create(CollectTag tag, User user) {
		tag.setUser(user);
		repository.save(tag);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void modify(Long id, CollectTag tag) {
		var rawTag = repository.getOne(id);
		rawTag.setName(tag.getName());
		rawTag.setSummary(tag.getSummary());
		repository.save(rawTag);
	}

	@Override
	public CollectTag get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.queryByTag_IdAndDeletedFalse(id, pageable);
	}

	@Override
	public Page<CollectTag> queryByUser(Long userId, Pageable pageable) {
		return repository.queryByUser_Id(userId, pageable);
	}

	@Override
	public Page<CollectTag> queryByUserAndName(Long userId, String name, Pageable pageable) {
		return repository.queryByUser_IdAndNameContains(userId, name, pageable);
	}

	@Override
	public boolean exists(CollectTag tag) {
		var userId = tag.getUser().getId();
		var name = tag.getName();
		return repository.existsByUser_IdAndName(userId, name);
	}
}
