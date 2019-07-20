package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

interface CollectCategoryRepository : JpaRepository<CollectCategory, Long> {
	fun findByName(name: String): Optional<CollectCategory>
	
	fun findByUserId(userId: Long, pageable: Pageable): Page<CollectCategory>
	
	fun countByUserId(userId: Long): Long
	
	fun findByUserIdAndNameContains(userId: Long, name: String, pageable: Pageable): Page<CollectCategory>
	
	fun existsByUserIdAndName(userId: Long, name: String): Boolean
}
