package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface HistoryRepository : JpaRepository<History, Long> {
	fun deleteByCollectIdAndUserId(collectId: Long, userId: Long)
	
	fun deleteAllByUserId(userId: Long)
	
	//NOTE 需要按id倒序排序显示
	fun findAllByUserIdOrderByIdDesc(userId: Long, pageable: Pageable): Page<History>
}
