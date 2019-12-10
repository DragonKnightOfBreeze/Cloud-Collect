package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CollectRepository : JpaRepository<Collect, Long> {
	fun findByNameAndUserId(name: String, userId: Long): Collect?
	
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryName(categoryName: String, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect>
	
	fun findAllByTagsId(tagId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTagsName(tagName: String, pageable: Pageable): Page<Collect>
	
	fun findAllByTagsNameContains(tagName: String, pageable: Pageable): Page<Collect>
	
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryNameContainsAndUserId(categoryName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTagsNameContainsAndUserId(tagName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByPraiseByUsersId(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByNameContainsAndPraiseByUsersId(name: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryNameContainsAndPraiseByUsersId(categoryName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTagsNameContainsAndPraiseByUsersId(tagName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByTypeContainsAndPraiseByUsersId(type: CollectType, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	fun countByCategoryId(categoryId: Long): Long
	
	fun countByTagsId(tagId: Long): Long
	
	fun countByUserId(userId: Long): Long
	
	fun countByPraiseByUsersId(praiseByUserId: Long): Long
	
	fun existsByNameAndUser(name: String, user: User): Boolean
	
	fun existsByIdAndPraiseByUsers(id: Long, praiseByUser: User): Boolean
}
