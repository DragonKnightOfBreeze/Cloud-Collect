package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

/**通知的仓库。*/
interface NoticeRepository : JpaRepository<Notice, Long> {
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice>
	
	fun countByUserId(userId: Long): Long
	
	fun findAllByUserIdAndReadStatus(userId: Long, readStatus: Boolean, pageable: Pageable): Page<Notice>
	
	fun countByUserIdAndReadStatus(userId: Long, readStatus: Boolean): Long
}
