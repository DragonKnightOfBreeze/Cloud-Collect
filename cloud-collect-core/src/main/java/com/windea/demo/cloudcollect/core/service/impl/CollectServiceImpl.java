package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CollectServiceImpl implements CollectService {
	private final CollectRepository repository;

	public CollectServiceImpl(CollectRepository repository) {
		this.repository = repository;
	}


	@Override
	public void create(Collect collect, Long userId) {

	}

	@Override
	public void createFrom(Collect collect, Long userId, Long fromUserId) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void modify(Long id, Collect collect) {

	}

	@Override
	public void modifyCategory(Long id, CollectCategory category) {

	}

	@Override
	public void modifyTags(Long id, Set<CollectTag> tags) {

	}

	@Override
	public void modifyType(Long id, CollectType type) {

	}

	@Override
	public void praise(Long id, Long userId) {

	}

	@Override
	public Collect get(Long id) {
		return null;
	}

	@Override
	public Page<Collect> queryByUser(Long userId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByUserDeleted(Long userId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByUserAndName(Long userId, String name, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByUserAndCategory(Long userId, Long categoryId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByUserAndTag(Long userId, Long categoryId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByUserAndType(Long userId, CollectType type, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByName(String name, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Collect> queryByPraiseByUser(Long praiseByUserId, Pageable pageable) {
		return null;
	}

	@Override
	public boolean exists(Long userId, String name) {
		return false;
	}

	@Override
	public void noticeFollowByUser() {

	}
}
