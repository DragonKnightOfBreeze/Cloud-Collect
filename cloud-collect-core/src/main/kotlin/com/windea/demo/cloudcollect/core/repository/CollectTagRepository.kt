package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

/**收藏的标签的仓库。*/
interface CollectTagRepository : JpaRepository<CollectTag, Long> {
	fun findByNameAndUserId(name: String, userId: Long): Optional<CollectTag>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag>
	
	fun countByUserId(userId: Long): Long
	
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
}
