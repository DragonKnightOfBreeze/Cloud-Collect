package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface UserRepository : JpaRepository<User, Long> {
	fun findByUsername(username: String): User?
	
	fun findByEmail(email: String): User?
	
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	fun findAllByUsernameContains(username: String, pageable: Pageable): Page<User>
	
	fun findAllByEmailContains(email: String, pageable: Pageable): Page<User>
	
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	fun findAllByFollowToUsersId(followToUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByNicknameContainsAndFollowToUsersId(nickname: String, followToUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByFollowByUsersId(followByUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByNicknameContainsAndFollowByUsersId(nickname: String, followByUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByPraiseToCollectsId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	fun countByFollowByUsersId(followByUserId: Long): Long
	
	fun countByFollowToUsersId(followToUserId: Long): Long
	
	fun countByPraiseToCollectsId(praiseToCollectId: Long): Long
	
	fun existsByUsername(username: String): Boolean
	
	fun existsByEmail(email: String): Boolean
	
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
	
	fun existsByIdAndFollowByUsers(id: Long, followByUser: User): Boolean
}
