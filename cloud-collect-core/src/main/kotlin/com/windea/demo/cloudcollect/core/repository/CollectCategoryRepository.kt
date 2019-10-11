package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CollectCategoryRepository : JpaRepository<CollectCategory, Long> {
	fun findByNameAndUserId(name: String, userId: Long): CollectCategory?
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectCategory>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory>
	
	fun countByUserId(userId: Long): Long
	
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
}
