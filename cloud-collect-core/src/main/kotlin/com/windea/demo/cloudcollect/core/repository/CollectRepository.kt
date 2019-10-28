package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CollectRepository : JpaRepository<Collect, Long> {
	fun findByNameAndUserId(name: String, userId: Long): Collect?
	
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect>
	
	@Query("from Collect c, in(c.tags) t where t.name=?1")
	fun findAllByTagNameContains(tagName: String, pageable: Pageable): Page<Collect>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryNameContainsAndUserId(categoryName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	@Query("from Collect c, in(c.tags) t where t.name=?1 and t.user.id = ?2")
	fun findAllByTagNameContainsAndUserId(tagName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect>
	
	@Query("from Collect c, in(c.tags) t where t.id=?1")
	fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	@Query("from Collect c, in(c.praiseByUserList) u where u.id=?1")
	fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun countByCategoryId(categoryId: Long): Long
	
	@Query("select count(c) from Collect c, in(c.tags) t where t.id=?1")
	fun countByTagId(tagId: Long): Long
	
	fun countByTypeAndUserId(type: CollectType, userId: Long): Long
	
	fun countByUserId(userId: Long): Long
	
	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=?1")
	fun countByPraiseByUserId(praiseByUserId: Long): Long
	
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
	
	fun existByIdAndPraiseByUserListContains(id: Long, praiseByUser: User): Boolean
}
