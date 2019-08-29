package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*

/**收藏的服务。*/
interface CollectService {
	/**创建自己的收藏。*/
	fun create(collect: Collect, user: User): Collect
	
	/**从别人的收藏创建自己的收藏。默认点赞原始收藏。*/
	fun createFrom(id: Long, user: User): Collect
	
	/**删除自己的收藏。*/
	fun delete(id: Long)
	
	/**修改自己的收藏。包括名字，概述，分类，标签，类型。*/
	fun modify(id: Long, collect: Collect): Collect
	
	/**修改自己的收藏的分类。*/
	fun modifyCategory(id: Long, category: CollectCategory): Collect
	
	/**修改自己的收藏的标签。*/
	fun modifyTags(id: Long, tags: MutableSet<CollectTag>): Collect
	
	/**修改自己的收藏的类型。*/
	fun modifyType(id: Long, type: CollectType): Collect
	
	/**点赞某一收藏。*/
	fun praise(id: Long, user: User): Collect
	
	/**根据id得到某一收藏。*/
	fun findById(id: Long): Collect
	
	/**根据名字、用户id得到某一收藏。*/
	fun findByNameAndUserId(name: String, userId: Long): Collect
	
	/**得到随机收藏。*/
	fun findByRandom(): Collect
	
	/**得到所有收藏。*/
	fun findAll(pageable: Pageable): Page<Collect>
	
	/**根据名字模糊查询所有收藏。*/
	fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect>
	
	/**根据名字和用户id模糊查询所有收藏。*/
	fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类id查询所有收藏。*/
	fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect>
	
	/**根据标签id查询所有收藏。*/
	fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect>
	
	/**根据类型和用户id查询所有收藏。*/
	fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据用户id查询所有收藏。*/
	fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect>
	
	/**检查某一收藏是否已存在。*/
	fun existsByNameAndUserId(name: String, userId: Long): Boolean
	
	
	/**得到该收藏的点赞用户数量。*/
	fun getPraiseByUserCount(id: Long): Long
	
	/**得到该收藏的评论数量。*/
	fun getCommentCount(id: Long): Long
	
	/**得到该收藏的所有点赞用户。*/
	fun getPraiseByUserPage(id: Long, pageable: Pageable): Page<User>
	
	/**得到该收藏的所有评论。*/
	fun getCommentPage(id: Long, pageable: Pageable): Page<Comment>
}
