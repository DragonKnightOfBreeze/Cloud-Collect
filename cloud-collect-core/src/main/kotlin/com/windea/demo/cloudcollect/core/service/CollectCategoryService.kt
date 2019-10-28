package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface CollectCategoryService {
	/**创建自己的分类。*/
	fun create(category: CollectCategory, user: User)
	
	/**修改自己的分类。*/
	fun modify(category: CollectCategory)
	
	/**删除自己的分类。*/
	fun deleteById(id: Long)
	
	/**根据id得到某一分类。*/
	fun findById(id: Long): CollectCategory
	
	/**得到所有分类。*/
	fun findAll(pageable: Pageable): Page<CollectCategory>
	
	/**根据名字和用户id模糊查询所有分类。*/
	fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<CollectCategory>
	
	/**根据用户id查询所有分类。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory>
	
	/**检查某一分类是否已存在。*/
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
	
	
	/**得到该分类的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
}
