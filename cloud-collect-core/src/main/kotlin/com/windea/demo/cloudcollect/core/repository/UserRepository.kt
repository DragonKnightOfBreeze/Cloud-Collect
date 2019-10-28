package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface UserRepository : JpaRepository<User, Long> {
	fun findByUsername(username: String): User?
	
	fun findByEmail(email: String): User?
	
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	@Query("from User u, in (u.followToUserList) fu where fu.id=?1")
	fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User>
	
	@Query("from User u, in (u.followByUserList) fu where fu.id=?1")
	fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User>
	
	@Query("from User u, in(u.praiseToCollectList) c where c.id=?1")
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	@Query("select count(u) from User u, in (u.followByUserList) fu where fu.id=?1")
	fun countByFollowByUserId(followByUserId: Long): Long
	
	@Query("select count(u) from User u, in (u.followToUserList) fu where fu.id=?1")
	fun countByFollowToUserId(followToUserId: Long): Long
	
	@Query("select count(u) from User u, in(u.praiseToCollectList) c where c.id=?1")
	fun countByPraiseToCollectId(praiseToCollectId: Long): Long
	
	fun existsByUsername(username: String): Boolean
	
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
	
	fun existsByIdAndFollowByUserListContains(id: Long, followByUser: User): Boolean
}
