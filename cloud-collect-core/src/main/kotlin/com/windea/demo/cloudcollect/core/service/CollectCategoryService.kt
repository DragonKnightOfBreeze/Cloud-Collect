package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/** 收藏的分类的服务。*/
interface CollectCategoryService {
	/** 创建自己的分类。*/
	fun create(category: CollectCategory, user: User): CollectCategory
	
	/** 删除自己的分类。*/
	fun delete(id: Long)
	
	/** 修改自己的分类。名字，概述。*/
	fun modify(id: Long, category: CollectCategory): CollectCategory
	
	/** 得到某一分类。*/
	fun get(id: Long): CollectCategory
	
	/** 分页得到某一分类的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
	
	/** 得到某一分类的收藏数量。*/
	fun getCollectCount(id: Long): Long
	
	/** 分页得到所有分类。*/
	fun findAll(pageable: Pageable): Page<CollectCategory>
	
	/** 分页得到某一用户的所有分类。*/
	fun findByUser(userId: Long, pageable: Pageable): Page<CollectCategory>
	
	/** 根据名字分页模糊查询某一用户的所有分类。*/
	fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<CollectCategory>
	
	/** 检查某一分类是否已存在。*/
	fun exists(category: CollectCategory): Boolean
}
