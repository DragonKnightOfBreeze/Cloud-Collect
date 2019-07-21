package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/**收藏的分类的服务。*/
interface CollectCategoryService {
	/**创建自己的分类。*/
	fun create(category: CollectCategory, user: User): CollectCategory
	
	/**删除自己的分类。*/
	fun delete(id: Long)
	
	/**修改自己的分类。包括名字，概述。*/
	fun modify(id: Long, category: CollectCategory): CollectCategory
	
	/**根据id得到某一分类。*/
	fun findById(id: Long): CollectCategory
	
	/**根据名字和用户id得到某一分类。*/
	fun findByNameAndUserId(name: String, userId: Long): CollectCategory
	
	/**分页得到所有分类。*/
	fun findAll(pageable: Pageable): Page<CollectCategory>
	
	/**根据名字和用户id分页模糊查询所有分类。*/
	fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<CollectCategory>
	
	/**根据用户id分页查询所有分类。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory>
	
	/**根据用户id得到分类数量。 */
	fun countByUserId(userId: Long): Long
	
	/**检查某一分类是否已存在。*/
	fun exists(category: CollectCategory): Boolean
}
