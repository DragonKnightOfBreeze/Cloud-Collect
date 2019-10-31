package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface CategoryService {
	/**创建自己的分类。*/
	fun create(category: Category, user: User)
	
	/**修改自己的分类。*/
	fun modify(category: Category)
	
	/**删除自己的分类。*/
	fun deleteById(id: Long)
	
	/**根据id得到某一分类。*/
	fun findById(id: Long): Category
	
	/**得到所有分类。*/
	fun findAll(pageable: Pageable): Page<Category>
	
	/**根据名字模糊查询所有分类。*/
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Category>
	
	/**根据名字和用户id模糊查询所有分类。*/
	fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<Category>
	
	/**根据用户id查询所有分类。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Category>
	
	/**检查某一分类是否已存在。*/
	fun existsByNameAndUser(name: String, user: User): Boolean
	
	
	/**得到该分类的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
}
