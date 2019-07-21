package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
	fun findByUsername(username: String): Optional<User>
	
	fun findByEmail(email: String): Optional<User>
	
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	@Query("from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User>
	
	@Query("select count(u) from User u, in (u.followToUserList) fu where fu.id=:followToUserId")
	fun countByFollowToUserId(followToUserId: Long): Long
	
	@Query("from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User>
	
	@Query("select count(u) from User u, in (u.followByUserList) fu where fu.id=:followByUserId")
	fun countByFollowByUserId(followByUserId: Long): Long
	
	@Query("from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	@Query("select count(u) from User u, in(u.praiseToCollectList) c where c.id=:praiseToCollectId")
	fun countByPraiseToCollectId(praiseToCollectId: Long): Long
	
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
}
