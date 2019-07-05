package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.exception.NotImplementedException;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


	@Override
	public void create(Collect collect, User user) {
		collect.setUser(user);
		repository.save(collect);

		noticeFriends();
	}

	@Override
	public void createFrom(Collect collect, User user) {
		praise(collect.getId(), user);

		collect.setId(null);
		collect.setUser(user);
		repository.save(collect);

		noticeFriends();
	}

	@Override
	public void delete(Long id) {
		var collect = repository.getOne(id);
		collect.setDeleted(true);
		repository.save(collect);
	}

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

	@Override
	public void modifyCategory(Long id, CollectCategory category) {
		var rawCollect = repository.getOne(id);
		rawCollect.setCategory(category);
		repository.save(rawCollect);
	}

	@Override
	public void modifyTags(Long id, Set<CollectTag> tags) {
		var rawCollect = repository.getOne(id);
		rawCollect.setTags(tags);
		repository.save(rawCollect);
	}

	@Override
	public void modifyType(Long id, CollectType type) {
		var rawCollect = repository.getOne(id);
		rawCollect.setType(type);
		repository.save(rawCollect);
	}

	@Override
	public void praise(Long id, User user) {
		var collect = repository.getOne(id);
		var praiseByUserList = collect.getPraiseByUserList();
		praiseByUserList.add(user);
		collect.setPraiseByUserList(praiseByUserList);
		repository.save(collect);
	}

	@Override
	public Collect get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<User> getPraiseByUserPage(Long id, Pageable pageable) {
		return userRepository.queryByPraiseToCollect_Id(id, pageable);
	}

	@Override
	public Page<Comment> getCommentPage(Long id, Pageable pageable) {
		return commentRepository.queryByCollect_Id(id, pageable);
	}

	@Override
	public Page<Collect> queryByUser(Long userId, Pageable pageable) {
		return repository.queryByUser_IdAndDeletedFalse(userId, pageable);
	}

	@Override
	public Page<Collect> queryByUserDeleted(Long userId, Pageable pageable) {
		return repository.queryByUser_IdAndDeletedTrue(userId, pageable);
	}

	@Override
	public Page<Collect> queryByUserAndName(Long userId, String name, Pageable pageable) {
		return repository.queryByUser_IdAndNameContainsAndDeletedFalse(userId, name, pageable);
	}

	@Override
	public Page<Collect> queryByUserAndCategory(Long categoryId, Pageable pageable) {
		return repository.queryByCategory_IdAndDeletedFalse(categoryId, pageable);
	}

	@Override
	public Page<Collect> queryByUserAndTag(Long tagId, Pageable pageable) {
		return repository.queryByTag_IdAndDeletedFalse(tagId, pageable);
	}

	@Override
	public Page<Collect> queryByUserAndType(Long userId, CollectType type, Pageable pageable) {
		return repository.queryByUser_IdAndTypeAndDeletedFalse(userId, type, pageable);
	}

	@Override
	public Page<Collect> queryByName(String name, Pageable pageable) {
		return repository.queryByNameContainsAndDeletedFalse(name, pageable);
	}

	@Override
	public Page<Collect> queryByPraiseByUser(Long praiseUserId, Pageable pageable) {
		return repository.queryByPraiseByUser_IdAndDeletedFalse(praiseUserId, pageable);
	}

	@Override
	public boolean exists(Collect collect) {
		var userId = collect.getUser().getId();
		var name = collect.getName();
		return repository.existsByUser_IdAndName(userId, name);
	}

	@Override
	public void noticeFriends() {
		throw new NotImplementedException();
	}
}
