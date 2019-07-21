package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/**通知的服务类。*/
interface NoticeService {
	/**创建某一用户的通知。*/
	fun create(notice: Notice, user: User): Notice
	
	/**创建通知，发送给所有用户。*/
	fun sendToAll(notice: Notice)
	
	/**删除通知。*/
	fun delete(id: Long)
	
	/**阅读自己的通知。将isRead设为true。*/
	fun read(id: Long): Notice
	
	/**根据id得到某一通知。*/
	fun findById(id: Long): Notice
	
	/**分页得到所有通知。*/
	fun findAll(pageable: Pageable): Page<Notice>
	
	/**根据用户id分页查询所有通知。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice>
	
	/**根据用户id得到通知数量。*/
	fun countByUserId(userId: Long): Long
	
	/**根据用户id和阅读状态分页查询所有通知。*/
	fun findAllByUserIdAndRead(userId: Long, isRead: Boolean, pageable: Pageable): Page<Notice>
	
	/**根据用户id和阅读状态得到通知数量。*/
	fun countByUserIdAndRead(userId: Long, isRead: Boolean): Long
}
