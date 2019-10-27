package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface HistoryService {
	/**创建浏览记录。*/
	fun create(history: History, user: User): History
	
	/**删除一条浏览记录。*/
	fun deleteById(id: Long)
	
	/**删除某一用户的所有浏览记录。*/
	fun deleteAllByUserId(userId: Long)
	
	/**得到某一用户的所有浏览记录。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<History>
}
