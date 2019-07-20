package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import java.util.*

interface CollectRepository : JpaRepository<Collect, Long> {
	fun findByName(name: String): Optional<Collect>
	
	fun findByUserIdAndDeleteStatus(userId: Long, deleteStatus: Boolean, pageable: Pageable): Page<Collect>
	
	fun countByUserIdAndDeleteStatus(userId: Long, deleteStatus: Boolean): Long
	
	fun findByUserIdAndNameContainsAndDeleteStatusFalse(userId: Long, name: String, pageable: Pageable): Page<Collect>
	
	fun findByCategoryIdAndDeleteStatusFalse(categoryId: Long, pageable: Pageable): Page<Collect>
	
	fun countByCategoryIdAndDeleteStatusFalse(categoryId: Long): Long
	
	@Query("from Collect c, in(c.tags) t where t.id=:tagId and c.deleteStatus=false")
	fun findByTagIdAndDeleteStatusFalse(tagId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.tags) t where t.id=:tagId and c.deleteStatus=false")
	fun countByTagIdAndDeleteStatusFalse(tagId: Long): Long
	
	fun findByUserIdAndTypeAndDeleteStatusFalse(userId: Long, type: CollectType, pageable: Pageable): Page<Collect>
	
	fun countByUserIdAndTypeAndDeleteStatusFalse(userId: Long, type: CollectType): Long
	
	@Query("from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.deleteStatus=false")
	fun findByPraiseByUserIdAndDeleteStatusFalse(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c" + ".deleteStatus=false")
	fun countByPraiseByUserIdAndDeleteStatusFalse(praiseByUserId: Long): Long
	
	fun findByNameContainsAndDeleteStatusFalse(name: String, pageable: Pageable): Page<Collect>
	
	fun existsByUserIdAndName(userId: Long, name: String): Boolean
}
