package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface NoticeService {
	/**创建一条通知。*/
	fun create(notice: Notice, user: User)
	
	/**删除一条通知。*/
	fun deleteById(id: Long)
	
	/**删除某一用户的所有通知。*/
	fun deleteAllByUserId(userId: Long)
	
	/**得到某一用户的所有通知。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice>
}
