package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UseServiceImpl implements UserService {
	private final UserRepository repository;
	private final CollectRepository collectRepository;
	private final CommentRepository commentRepository;
	private final NoticeRepository noticeRepository;

	public UseServiceImpl(UserRepository repository, CollectRepository collectRepository,
		CommentRepository commentRepository, NoticeRepository noticeRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
		this.commentRepository = commentRepository;
		this.noticeRepository = noticeRepository;
	}


	@Override
	public void register(User user) {
		repository.save(user);
	}

	@Override
	public void activate(Long userId) {
		var user = repository.getOne(userId);
		user.setActivated(true);
		repository.save(user);
	}

	@Override
	public void update(Long id, User user) {
		var rawUser = repository.getOne(id);
		rawUser.setNickname(user.getNickname());
		rawUser.setIntroduce(user.getIntroduce());
		rawUser.setAvatarUrl(user.getAvatarUrl());
		rawUser.setBackgroundUrl(user.getBackgroundUrl());
	}

	@Override
	public User get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<User> getFollowToUserPage(Long id, Pageable pageable) {
		return repository.queryByFollowByUser_Id(id, pageable);
	}

	@Override
	public Page<User> getFollowByUserPage(Long id, Pageable pageable) {
		return repository.queryByFollowToUser_Id(id, pageable);
	}

	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.queryByUser_IdAndDeletedFalse(id, pageable);
	}

	@Override
	public Page<Comment> getCommentPage(Long id, Pageable pageable) {
		return commentRepository.queryBySponsorByUser_Id(id, pageable);
	}

	@Override
	public Page<Notice> getNoticePage(Long id, Pageable pageable) {
		return noticeRepository.queryByUser_Id(id, pageable);
	}

	@Override
	public Page<User> queryByNickname(String nickname, Pageable pageable) {
		return repository.queryByNicknameContains(nickname, pageable);
	}

	@Override
	public Page<User> queryByRole(Role role, Pageable pageable) {
		return repository.queryByRole(role, pageable);
	}

	@Override
	public Page<User> queryByFollowToUser(Long followToUserId, Pageable pageable) {
		return repository.queryByFollowToUser_Id(followToUserId, pageable);
	}

	@Override
	public Page<User> queryByFollowByUser(Long followByUserId, Pageable pageable) {
		return repository.queryByFollowByUser_Id(followByUserId, pageable);
	}

	@Override
	public Page<User> queryByPraiseToCollect(Long praiseToCollectId, Pageable pageable) {
		return repository.queryByPraiseToCollect_Id(praiseToCollectId, pageable);
	}

	@Override
	public boolean exists(User user) {
		var username = user.getUsername();
		var email = user.getEmail();
		return repository.existsByUsernameOrEmail(username, email);
	}
}
