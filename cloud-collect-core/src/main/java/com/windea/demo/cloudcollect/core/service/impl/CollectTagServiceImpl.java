package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
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
	public void create(CollectTag tag) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void modify(Long id, CollectTag tag) {

	}

	@Override
	public CollectTag get(Long id) {
		return null;
	}

	@Override
	public Page<CollectTag> queryByUser(Long userId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<CollectTag> queryByUserAndName(Long userId, String name, Pageable pageable) {
		return null;
	}

	@Override
	public boolean exists(Long userId, String name) {
		return false;
	}
}
