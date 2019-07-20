package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/** 收藏的标签的服务。*/
interface CollectTagService {
	/** 创建自己的标签。*/
	fun create(tag: CollectTag, user: User): CollectTag
	
	/** 删除自己的标签。*/
	fun delete(id: Long)
	
	/** 修改自己的标签。名字，概述。*/
	fun modify(id: Long, tag: CollectTag): CollectTag
	
	/** 得到某一标签。*/
	fun get(id: Long): CollectTag
	
	/** 分页得到某一标签的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
	
	/** 得到某一标签的收藏数量。*/
	fun getCollectCount(id: Long): Long
	
	/** 分页得到所有标签。*/
	fun findAll(pageable: Pageable): Page<CollectTag>
	
	/** 分页查询某一用户的所有标签。*/
	fun findByUser(userId: Long, pageable: Pageable): Page<CollectTag>
	
	/** 根据名字分页模糊查询某一用户的所有标签。*/
	fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<CollectTag>
	
	/** 检查某一标签是否已存在。*/
	fun exists(tag: CollectTag): Boolean
}
