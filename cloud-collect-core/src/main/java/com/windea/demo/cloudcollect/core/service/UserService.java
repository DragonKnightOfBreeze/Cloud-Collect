package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.domain.request.LoginView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	void register(User user);

	void activate(Long userId);

	void login(LoginView loginView);

	void updateInformation(Long id, User user);

	User getInformation(Long id);

	Page<User> queryByNickname(String nickname, Pageable pageable);

	Page<User> queryByRole(Role role, Pageable pageable);

	Page<User> queryByFollowToUser(Long followToUserId, Pageable pageable);

	Page<User> queryByFollowByUser(Long followByUserId, Pageable pageable);

	Page<User> queryByPraiseToCollect(Long praiseToCollectId, Pageable pageable);

	boolean exists(String username, String email);
}
