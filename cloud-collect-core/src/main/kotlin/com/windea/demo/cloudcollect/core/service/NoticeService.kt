package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/** 通知的服务类。*/
interface NoticeService {
	/** 创建某一用户的通知。*/
	fun create(notice: Notice, user: User): Notice
	
	/** 创建通知，发送给所有用户。*/
	fun sendToAll(notice: Notice)
	
	/** 删除通知。*/
	fun delete(id: Long)
	
	/** 阅读自己的通知。将readStatus设为true。*/
	fun read(id: Long): Notice
	
	/** 得到某一通知。*/
	fun get(id: Long): Notice
	
	/** 分页得到所有通知。*/
	fun findAll(pageable: Pageable): Page<Notice>
	
	/** 分页查询某一用户的所有通知。*/
	fun findByUser(userId: Long, pageable: Pageable): Page<Notice>
	
	/** 分页查询某一用户的所有已读/未读通知。*/
	fun findByUserAndReadStatus(userId: Long, readStatus: Boolean, pageable: Pageable): Page<Notice>
}
