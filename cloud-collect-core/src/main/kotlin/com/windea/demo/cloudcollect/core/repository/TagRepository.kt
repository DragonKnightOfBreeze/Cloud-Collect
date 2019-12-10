package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface TagRepository : JpaRepository<Tag, Long> {
	fun findByName(name: String): Tag?
	
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Tag>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Tag>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Tag>
	
	fun existsByName(name: String): Boolean
}
