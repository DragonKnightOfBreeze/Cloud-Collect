package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.domain.response.UserFollowView;
import com.windea.demo.cloudcollect.core.domain.response.UserPraiseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);

	Page<User> queryByNicknameContains(String nickname, Pageable pageable);

	Page<User> queryByRole(Role role, Pageable pageable);

	boolean existsByUsernameOrEmail(String username, String email);

	@Query("select u.id, u.nickname, u.followToUserList, u.followByUserList from User u where u.id = :id")
	UserFollowView getFollowView(Long id);

	@Query("select u.id, u.nickname, u.praiseToCollectList from User u where u.id = :id")
	UserPraiseView getPraiseView(Long id);
}
