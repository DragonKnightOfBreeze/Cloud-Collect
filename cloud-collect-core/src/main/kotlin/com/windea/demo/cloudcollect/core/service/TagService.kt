package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface TagService {
	/**创建自己的标签。*/
	fun create(tag: Tag, user: User)
	
	/**修改自己的标签。包括名字，概述*/
	fun modify(id: Long, tag: Tag)
	
	/**删除自己的标签。*/
	fun deleteById(id: Long)
	
	/**根据id得到某一标签。*/
	fun findById(id: Long): Tag
	
	/**得到所有标签。*/
	fun findAll(pageable: Pageable): Page<Tag>
	
	/**得到所有分类。*/
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Tag>
	
	/**根据名字和用户id模糊查询所有标签。*/
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Tag>
	
	/**根据用户id查询所有标签。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Tag>
	
	/**检查某一标签是否已存在。*/
	fun existsByNameAndUser(name: String, user: User): Boolean
	
	
	/**得到该标签的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
}
