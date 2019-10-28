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
	
	fun findAllByFollowToUserListId(followToUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByFollowByUserListId(followByUserId: Long, pageable: Pageable): Page<User>
	
	fun findAllByPraiseToCollectListId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	fun countByFollowByUserListId(followByUserId: Long): Long
	
	fun countByFollowToUserListId(followToUserId: Long): Long
	
	fun countByPraiseToCollectListId(praiseToCollectId: Long): Long
	
	fun existsByUsername(username: String): Boolean
	
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
	
	fun existsByIdAndFollowByUserList(id: Long, followByUser: User): Boolean
}
