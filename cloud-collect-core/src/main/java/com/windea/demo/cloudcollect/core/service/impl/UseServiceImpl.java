package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.domain.request.LoginView;
import com.windea.demo.cloudcollect.core.repository.UserRepository;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UseServiceImpl implements UserService {
	private final UserRepository repository;

	public UseServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void register(User user) {

	}

	@Override
	public void activate(Long userId) {

	}

	@Override
	public void login(LoginView loginView) {

	}

	@Override
	public void updateInformation(Long id, User user) {

	}

	@Override
	public User getInformation(Long id) {
		return null;
	}

	@Override
	public Page<User> queryByNickname(String nickname, Pageable pageable) {
		return null;
	}

	@Override
	public Page<User> queryByRole(Role role, Pageable pageable) {
		return null;
	}

	@Override
	public Page<User> queryByFollowToUser(Long followToUserId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<User> queryByFollowByUser(Long followByUserId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<User> queryByPraiseToCollect(Long praiseToCollectId, Pageable pageable) {
		return null;
	}

	@Override
	public boolean exists(String username, String email) {
		return false;
	}
}
