package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CategoryRepository : JpaRepository<Category, Long> {
	fun findByNameAndUserId(name: String, userId: Long): Category?
	
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Category>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Category>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Category>
	
	fun countByUserId(userId: Long): Long
	
	fun existsByNameAndUser(name: String, user: User): Boolean
}
