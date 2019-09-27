package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

/**收藏的仓库。*/
interface CollectRepository : JpaRepository<Collect, Long> {
	fun findByNameAndUserId(name: String, userId: Long): Collect?
	
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect>
	
	fun countByCategoryId(categoryId: Long): Long
	
	@Query("from Collect c, in(c.tags) t where t.id=:tagId")
	fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.tags) t where t.id=:tagId")
	fun countByTagId(tagId: Long): Long
	
	fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	fun countByTypeAndUserId(type: CollectType, userId: Long): Long
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect>
	
	fun countByUserId(userId: Long): Long
	
	@Query("from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId")
	fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId")
	fun countByPraiseByUserId(praiseByUserId: Long): Long
	
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
}
