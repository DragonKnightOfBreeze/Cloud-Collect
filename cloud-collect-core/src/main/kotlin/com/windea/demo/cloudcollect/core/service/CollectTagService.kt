package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/**收藏的标签的服务。*/
interface CollectTagService {
	/**创建自己的标签。*/
	fun create(tag: CollectTag, user: User): CollectTag
	
	/**删除自己的标签。*/
	fun delete(id: Long)
	
	/**修改自己的标签。包括名字，概述*/
	fun modify(id: Long, tag: CollectTag): CollectTag
	
	/**根据id得到某一标签。*/
	fun findById(id: Long): CollectTag
	
	/**根据名字和用户id得到某一标签。*/
	fun findByNameAndUserId(name: String, userId: Long): CollectTag
	
	/**得到所有标签。*/
	fun findAll(pageable: Pageable): Page<CollectTag>
	
	/**根据名字和用户id模糊查询所有标签。*/
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag>
	
	/**根据用户id查询所有标签。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag>
	
	/**检查某一标签是否已存在。*/
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
	
	
	/**得到该标签的收藏数量。*/
	fun getCollectCount(id: Long): Long
	
	/**得到该标签的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
}
