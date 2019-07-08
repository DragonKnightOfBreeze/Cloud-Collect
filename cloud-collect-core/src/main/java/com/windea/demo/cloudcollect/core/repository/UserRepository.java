package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Page<User> findByNicknameContains(String nickname, Pageable pageable);

	Page<User> findByRole(Role role, Pageable pageable);

	@Query("from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	Page<User> findByFollowToUser_Id(Long followToUserId, Pageable pageable);

	@Query("select count(u) from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	long countByFollowToUser_Id(Long followToUserId);

	@Query("from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	Page<User> findByFollowByUser_Id(Long followByUserId, Pageable pageable);

	@Query("select count(u) from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	long countByFollowByUser_Id(Long followByUserId);

	@Query("from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	Page<User> findByPraiseToCollect_Id(Long praiseToCollectId, Pageable pageable);

	@Query("select count(u) from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	long countByPraiseToCollect_Id(Long praiseToCollectId);

	boolean existsByUsernameOrEmail(String username, String email);
}
