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
	
	/**分页得到所有标签。*/
	fun findAll(pageable: Pageable): Page<CollectTag>
	
	/**根据名字和用户id分页模糊查询所有标签。*/
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag>
	
	/**根据用户id分页查询所有标签。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag>
	
	/**根据用户id得到标签数量。 */
	fun countByUserId(userId: Long): Long
	
	/**检查某一标签是否已存在。*/
	fun exists(tag: CollectTag): Boolean
}
