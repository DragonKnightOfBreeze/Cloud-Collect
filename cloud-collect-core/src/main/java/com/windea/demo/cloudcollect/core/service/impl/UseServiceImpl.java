package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.commons.kotlin.extension.RandomExtension;
import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.domain.view.EmailRegisterView;
import com.windea.demo.cloudcollect.core.domain.view.UsernamePasswordLoginView;
import com.windea.demo.cloudcollect.core.exception.NotFoundException;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.EmailService;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UseServiceImpl implements UserService {
	private final UserRepository repository;
	private final CollectRepository collectRepository;
	private final CollectCategoryRepository categoryRepository;
	private final NoticeRepository noticeRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public UseServiceImpl(UserRepository repository, CollectRepository collectRepository,
		CollectCategoryRepository categoryRepository, NoticeRepository noticeRepository,
		EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.categoryRepository = categoryRepository;
		this.collectRepository = collectRepository;
		this.noticeRepository = noticeRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}


	@Override
	public User loginByUsernameAndPassword(UsernamePasswordLoginView view) {
		var auth = new UsernamePasswordAuthenticationToken(view.getUsername(), view.getPassword());
		var validAuthToken = authenticationManager.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(validAuthToken);

		var userDetails = (JwtUserDetails) validAuthToken.getPrincipal();
		return userDetails.getDelegateUser();
	}

	@Transactional
	@Override
	public User registerByEmail(EmailRegisterView view) {
		var user = new User();
		user.setNickname(view.getNickname());
		user.setUsername(view.getUsername());
		user.setEmail(view.getEmail());
		user.setPassword(passwordEncoder.encode(view.getPassword()));
		var result = repository.save(user);

		emailService.sendActivateEmail();

		return result;
	}

	@Transactional
	@Override
	public User activate(User user) {
		user.setActivateStatus(true);
		var result = repository.save(user);

		emailService.sendHelloEmail();

		return result;
	}

	@Transactional
	@Override
	public User resetPassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		return repository.save(user);
	}

	@Transactional
	@Override
	public User update(Long id, User user) {
		var rawUser = repository.getOne(id);
		rawUser.setNickname(user.getNickname());
		rawUser.setIntroduce(user.getIntroduce());
		rawUser.setAvatarUrl(user.getAvatarUrl());
		rawUser.setBackgroundUrl(user.getBackgroundUrl());
		return repository.save(user);
	}

	@Cacheable("user")
	@Override
	public User get(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public User getByRandom() {
		var count = repository.count();
		var randomId = RandomExtension.INSTANCE.range(1, count);
		return repository.findById(randomId).orElseThrow(NotFoundException::new);
	}

	@Cacheable("user.followToUserPage")
	@Override
	public Page<User> getFollowToUserPage(Long id, Pageable pageable) {
		return repository.findByFollowByUser_Id(id, pageable);
	}

	@Cacheable("user.followToUserCount")
	@Override
	public Long getFollowToUserCount(Long id) {
		return repository.countByFollowByUser_Id(id);
	}

	@Cacheable("user.followByUserPage")
	@Override
	public Page<User> getFollowByUserPage(Long id, Pageable pageable) {
		return repository.findByFollowToUser_Id(id, pageable);
	}

	@Cacheable("user.followByUserCount")
	@Override
	public Long getFollowByUserCount(Long id) {
		return repository.countByFollowToUser_Id(id);
	}

	@Cacheable("user.collectPage")
	@Override
	public Page<Collect> getCollectPage(Long id, Pageable pageable) {
		return collectRepository.findByUser_IdAndDeleteStatus(id, false, pageable);
	}

	@Cacheable("user.collectCount")
	@Override
	public Long getCollectCount(Long id) {
		return collectRepository.countByUser_IdAndDeleteStatus(id, false);
	}

	@Cacheable("user.collectCategoryPage")
	@Override
	public Page<CollectCategory> getCollectCategoryPage(Long id, Pageable pageable) {
		return categoryRepository.findByUser_Id(id, pageable);
	}

	@Cacheable("user.collectCategoryCount")
	@Override
	public Long getCollectCategoryCount(Long id) {
		return categoryRepository.countByUser_Id(id);
	}

	@Cacheable("user.noticePage")
	@Override
	public Page<Notice> getNoticePage(Long id, Pageable pageable) {
		return noticeRepository.findByUser_Id(id, pageable);
	}

	@Cacheable("collect.noticeCount")
	@Override
	public Long getNoticeCount(Long id) {
		return noticeRepository.countByUser_Id(id);
	}

	@Cacheable("collectPage")
	@Override
	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("collectPage.byNickname")
	@Override
	public Page<User> findByNickname(String nickname, Pageable pageable) {
		return repository.findByNicknameContains(nickname, pageable);
	}

	@Cacheable("collectPage.byRole")
	@Override
	public Page<User> findByRole(Role role, Pageable pageable) {
		return repository.findByRole(role, pageable);
	}

	@Override
	public boolean exists(User user) {
		var username = user.getUsername();
		var email = user.getEmail();
		return repository.existsByUsernameOrEmail(username, email);
	}
}
