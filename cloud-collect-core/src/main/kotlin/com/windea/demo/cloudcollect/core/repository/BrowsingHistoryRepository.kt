package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface BrowsingHistoryRepository : JpaRepository<BrowsingHistory, Long> {
	fun deleteAllByUserId(userId: Long)
	
	//NOTE 需要按id倒序排序显示
	fun findAllByUserIdOrderByIdDesc(userId: Long, pageable: Pageable): Page<BrowsingHistory>
}
