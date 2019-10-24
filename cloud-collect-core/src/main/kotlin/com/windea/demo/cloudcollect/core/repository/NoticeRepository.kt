package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface NoticeRepository : JpaRepository<Notice, Long> {
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice>
	
	fun findAllByUserIdAndReadStatus(userId: Long, readStatus: Boolean, pageable: Pageable): Page<Notice>
	
	fun countByUserId(userId: Long): Long
	
	fun countByUserIdAndReadStatus(userId: Long, readStatus: Boolean): Long
}
