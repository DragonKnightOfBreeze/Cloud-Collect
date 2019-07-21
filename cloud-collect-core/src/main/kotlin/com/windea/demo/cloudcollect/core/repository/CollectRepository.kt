package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

/**收藏的仓库。*/
interface CollectRepository : JpaRepository<Collect, Long> {
	fun findByNameAndUserIdAndDeleted(name: String, userId: Long, isDeleted: Boolean): Optional<Collect>
	
	fun findAllByNameContainsAndDeletedFalse(name: String, pageable: Pageable): Page<Collect>
	
	fun findAllByNameContainsAndUserIdAndDeletedFalse(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	fun findAllByCategoryIdAndDeletedFalse(categoryId: Long, pageable: Pageable): Page<Collect>
	
	fun countByCategoryIdAndDeletedFalse(categoryId: Long): Long
	
	@Query("from Collect c, in(c.tags) t where t.id=:tagId and c.isDeleted=false")
	fun findAllByTagIdAndDeletedFalse(tagId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.tags) t where t.id=:tagId and c.isDeleted=false")
	fun countByTagIdAndDeletedFalse(tagId: Long): Long
	
	fun findAllByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	fun countByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long): Long
	
	fun findAllByUserIdAndDeleted(userId: Long, isDeleted: Boolean, pageable: Pageable): Page<Collect>
	
	fun countByUserIdAndDeleted(userId: Long, isDeleted: Boolean): Long
	
	@Query("from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.isDeleted=false")
	fun findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.isDeleted=false")
	fun countByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long): Long
	
	fun existsByNameAndUserIdAndDeleted(name: String, userId: Long, isDeleted: Boolean): Boolean
}
