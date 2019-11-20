package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*

interface CollectService {
	/**创建自己的收藏。*/
	fun create(collect: Collect)
	
	/**从别人的收藏创建自己的收藏。默认点赞原始收藏。*/
	fun createFrom(collect: Collect, user: User)
	
	/**修改自己的收藏。包括名字，概述，分类，标签，类型。*/
	fun modify(id: Long, collect: Collect)
	
	/**点赞某一收藏。*/
	fun praise(id: Long, user: User)
	
	/**取消点赞某一收藏。*/
	fun unpraise(id: Long, user: User)
	
	/**删除自己的收藏。*/
	fun deleteById(id: Long)
	
	/**根据id得到某一收藏。*/
	fun findById(id: Long): Collect
	
	/**得到随机收藏。*/
	fun findByRandom(): Collect
	
	/**得到所有收藏。*/
	fun findAll(pageable: Pageable): Page<Collect>
	
	/**根据名字全局模糊查询所有收藏。*/
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect>
	
	/**根据分类id查询所有收藏。*/
	fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类名字全局模糊查询所有收藏。*/
	fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect>
	
	/**根据标签id查询所有收藏。*/
	fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect>
	
	/**根据标签名字全局模糊查询所有收藏。*/
	fun findAllByTagNameContains(tagName: String, pageable: Pageable): Page<Collect>
	
	/**根据用户id查询所有收藏。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据名字和用户id模糊查询所有收藏。*/
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类名字和用户id模糊查询所有收藏。*/
	fun findAllByCategoryNameContainsAndUserId(categoryName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据标签名字和用户id模糊查询所有收藏。*/
	fun findAllByTagNameContainsAndUserId(tagName: String, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据类型和用户id查询所有收藏。*/
	fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据点赞用户id查询所有收藏。*/
	fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**根据名字和点赞用户id模糊查询所有收藏。*/
	fun findAllByNameContainsAndPraiseByUserId(name: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类名字和点赞用户id模糊查询所有收藏。*/
	fun findAllByCategoryNameContainsAndPraiseByUserId(categoryName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**根据标签名字和点赞用户id模糊查询所有收藏。*/
	fun findAllByTypeAndPraiseByUserId(type: CollectType, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**根据类型和点赞用户id查询所有收藏。*/
	fun findAllByTagNameContainsAndPraiseByUserId(tagName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**检查某一收藏是否已存在。*/
	fun existsByNameAndUser(name: String, user: User): Boolean
}
