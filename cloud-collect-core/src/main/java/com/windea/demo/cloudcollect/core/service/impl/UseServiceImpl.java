package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UseServiceImpl implements UserService {
	private final UserRepository repository;
	private final CollectRepository collectRepository;
	private final CollectCategoryRepository categoryRepository;
	private final NoticeRepository noticeRepository;
	private final PasswordEncoder passwordEncoder;

	public UseServiceImpl(UserRepository repository, CollectRepository collectRepository,
		CollectCategoryRepository categoryRepository, NoticeRepository noticeRepository,
		PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.categoryRepository = categoryRepository;
		this.collectRepository = collectRepository;
		this.noticeRepository = noticeRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Transactional
	@Override
	public void register(User user) {
		var password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		repository.save(user);
	}

	@Transactional
	@Override
	public void activate(Long userId) {
		var user = repository.getOne(userId);
		user.setActivated(true);
		repository.save(user);
	}

	@Transactional
	@Override
	public void update(Long id, User user) {
		var rawUser = repository.getOne(id);
		rawUser.setNickname(user.getNickname());
		rawUser.setIntroduce(user.getIntroduce());
		rawUser.setAvatarUrl(user.getAvatarUrl());
		rawUser.setBackgroundUrl(user.getBackgroundUrl());
		repository.save(user);
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
	public Long getFollowToUserCount(Long id) {
		return repository.countByFollowByUser_Id(id);
	}

	@Override
	public Page<User> getFollowByUserPage(Long id, Pageable pageable) {
		return repository.queryByFollowToUser_Id(id, pageable);
	}

	@Override
	public Long getFollowByUserCount(Long id) {
		return repository.countByFollowToUser_Id(id);
	}

	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.queryByUser_IdAndDeleted(id, false, pageable);
	}

	@Override
	public Long getCollectCount(Long id) {
		return collectRepository.countByUser_IdAndDeleted(id, false);
	}

	@Override
	public Page<CollectCategory> getCollectCategoryPage(Long id, Pageable pageable) {
		return categoryRepository.queryByUser_Id(id, pageable);
	}

	@Override
	public Long getCollectCategoryCount(Long id) {
		return categoryRepository.countByUser_Id(id);
	}

	@Override
	public Page<Notice> getNoticePage(Long id, Pageable pageable) {
		return noticeRepository.queryByUser_Id(id, pageable);
	}

	@Override
	public Long getNoticeCount(Long id) {
		return noticeRepository.countByUser_Id(id);
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
	public boolean exists(User user) {
		var username = user.getUsername();
		var email = user.getEmail();
		return repository.existsByUsernameOrEmail(username, email);
	}
}
