package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

interface CollectTagRepository : JpaRepository<CollectTag, Long> {
	fun findByName(name: String): Optional<CollectTag>
	
	fun findByUserId(userId: Long, pageable: Pageable): Page<CollectTag>
	
	fun countByUserId(userId: Long): Long
	
	fun findByUserIdAndNameContains(userId: Long, name: String, pageable: Pageable): Page<CollectTag>
	
	fun existsByUserIdAndName(userId: Long, name: String): Boolean
}
