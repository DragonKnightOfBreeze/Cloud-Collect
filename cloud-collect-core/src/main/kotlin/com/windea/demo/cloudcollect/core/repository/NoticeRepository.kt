package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface NoticeRepository : JpaRepository<Notice, Long> {
	fun deleteAllByUserId(userId: Long)
	
	//需要按id倒序排序显示
	fun findAllByUserIdOrderByIdDesc(userId: Long, pageable: Pageable): Page<Notice>
	
	//fun countByUserId(userId: Long): Long
}
