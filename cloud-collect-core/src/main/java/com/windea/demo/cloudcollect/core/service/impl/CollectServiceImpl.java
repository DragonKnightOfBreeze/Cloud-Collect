package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.commons.kotlin.extension.StringExtensionsKt;
import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.exception.NotFoundException;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class CollectServiceImpl implements CollectService {
	private final CollectRepository repository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	public CollectServiceImpl(CollectRepository repository,
		UserRepository userRepository, CommentRepository commentRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}


	@Transactional
	@Override
	public void create(Collect collect, User user) {
		collect.setUrl(StringExtensionsKt.toUrlInfo(collect.getUrl()).getFullPath());
		if(collect.getLogoUrl() != null) {
			collect.setLogoUrl(StringExtensionsKt.toUrlInfo(collect.getLogoUrl()).getFullPath());
		}
		collect.setUser(user);
		repository.save(collect);
	}

	@Transactional
	@Override
	public void createFrom(Collect collect, User user) {
		praise(collect.getId(), user);

		create(collect, user);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		var collect = repository.getOne(id);
		collect.setDeleted(true);
		repository.save(collect);
	}

	@Transactional
	@Override
	public void modify(Long id, Collect collect) {
		var rawCollect = repository.getOne(id);
		rawCollect.setName(collect.getName());
		rawCollect.setSummary(collect.getSummary());
		rawCollect.setCategory(collect.getCategory());
		rawCollect.setTags(collect.getTags());
		rawCollect.setType(collect.getType());
		repository.save(rawCollect);
	}

	@Transactional
	@Override
	public void modifyCategory(Long id, CollectCategory category) {
		var rawCollect = repository.getOne(id);
		rawCollect.setCategory(category);
		repository.save(rawCollect);
	}

	@Transactional
	@Override
	public void modifyTags(Long id, Set<CollectTag> tags) {
		var rawCollect = repository.getOne(id);
		rawCollect.setTags(tags);
		repository.save(rawCollect);
	}

	@Transactional
	@Override
	public void modifyType(Long id, CollectType type) {
		var rawCollect = repository.getOne(id);
		rawCollect.setType(type);
		repository.save(rawCollect);
	}

	@Transactional
	@Override
	public void praise(Long id, User user) {
		var collect = repository.getOne(id);
		var praiseByUserList = collect.getPraiseByUserList();
		praiseByUserList.add(user);
		collect.setPraiseByUserList(praiseByUserList);
		repository.save(collect);

	}

	@Cacheable("collect")
	@Override
	public Collect get(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Cacheable("collect.praiseByUserPage")
	@Override
	public Page<User> getPraiseByUserPage(Long id, Pageable pageable) {
		return userRepository.findByPraiseToCollect_Id(id, pageable);
	}

	@Cacheable("collect.praiseByUserCount")
	@Override
	public Long getPraiseByUserCount(Long id) {
		return userRepository.countByPraiseToCollect_Id(id);
	}

	@Cacheable("collect.commentPage")
	@Override
	public Page<Comment> getCommentPage(Long id, Pageable pageable) {
		return commentRepository.findByCollect_Id(id, pageable);
	}

	@Cacheable("collect.commentCount")
	@Override
	public Long getCommentCount(Long id) {
		return commentRepository.countByCollect_Id(id);
	}

	@Cacheable("collectPage")
	@Override
	public Page<Collect> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("collectPage.byUserAndDeleted")
	@Override
	public Page<Collect> findByUserAndDeleted(Long userId, Boolean deleted, Pageable pageable) {
		return repository.findByUser_IdAndDeleted(userId, deleted, pageable);
	}

	@Cacheable("collectPage.byUserAndName")
	@Override
	public Page<Collect> findByUserAndName(Long userId, String name, Pageable pageable) {
		return repository.findByUser_IdAndNameContainsAndDeletedFalse(userId, name, pageable);
	}

	@Cacheable("collectPage.byUserAndCategory")
	@Override
	public Page<Collect> findByUserAndCategory(Long categoryId, Pageable pageable) {
		return repository.findByCategory_IdAndDeletedFalse(categoryId, pageable);
	}

	@Cacheable("collectPage.byUserAndTag")
	@Override
	public Page<Collect> findByUserAndTag(Long tagId, Pageable pageable) {
		return repository.findByTag_IdAndDeletedFalse(tagId, pageable);
	}

	@Cacheable("collectPage.byUserAndType")
	@Override
	public Page<Collect> findByUserAndType(Long userId, CollectType type, Pageable pageable) {
		return repository.findByUser_IdAndTypeAndDeletedFalse(userId, type, pageable);
	}

	@Cacheable("collectPage.byName")
	@Override
	public Page<Collect> findByName(String name, Pageable pageable) {
		return repository.findByNameContainsAndDeletedFalse(name, pageable);
	}

	@Override
	public boolean exists(Collect collect) {
		var userId = collect.getUser().getId();
		var name = collect.getName();
		return repository.existsByUser_IdAndName(userId, name);
	}
}
