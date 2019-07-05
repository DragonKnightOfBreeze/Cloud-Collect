package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);

	Page<User> queryByNicknameContains(String nickname, Pageable pageable);

	Page<User> queryByRole(Role role, Pageable pageable);

	@Query("from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	Page<User> queryByFollowToUser_Id(Long followToUserId, Pageable pageable);

	@Query("select count(u) from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	Long countByFollowToUser_Id(Long followToUserId);

	@Query("from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	Page<User> queryByFollowByUser_Id(Long followByUserId, Pageable pageable);

	@Query("select count(u) from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	Long countByFollowByUser_Id(Long followByUserId);

	@Query("from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	Page<User> queryByPraiseToCollect_Id(Long praiseToCollectId, Pageable pageable);

	@Query("select count(u) from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	Long countByPraiseToCollect_Id(Long praiseToCollectId);

	boolean existsByUsernameOrEmail(String username, String email);
}
