package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*

/**收藏的服务。*/
interface CollectService {
	/**创建自己的收藏。去除地址的查询参数。*/
	fun create(collect: Collect, user: User): Collect
	
	/**从别人的收藏创建自己的收藏。默认点赞原始收藏，去除地址的查询参数。*/
	fun createFrom(id: Long, user: User): Collect
	
	/**删除自己的收藏。不删除数据库中的数据，而是将isDeleted设为true。*/
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
	
	/**根据名字、用户id和删除状态得到某一收藏。*/
	fun findByNameAndUserIdAndDeleted(name: String, userId: Long, isDeleted: Boolean): Collect
	
	/**得到随机收藏。*/
	fun findByRandom(): Collect
	
	/**分页得到所有收藏。*/
	fun findAll(pageable: Pageable): Page<Collect>
	
	/**根据名字分页模糊查询所有未删除收藏。*/
	fun findAllByNameContainsAndDeletedFalse(name: String, pageable: Pageable): Page<Collect>
	
	/**根据名字和用户id分页模糊查询所有未删除收藏。*/
	fun findAllByNameContainsAndUserIdAndDeletedFalse(name: String, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类id分页查询所有未删除收藏。*/
	fun findAllByCategoryIdAndDeletedFalse(categoryId: Long, pageable: Pageable): Page<Collect>
	
	/**根据分类id得到收藏数量。*/
	fun countByCategoryIdAndDeletedFalse(categoryId: Long): Long
	
	/**根据标签id分页查询所有未删除收藏。*/
	fun findAllByTagIdAndDeletedFalse(tagId: Long, pageable: Pageable): Page<Collect>
	
	/**根据标签id得到收藏数量。*/
	fun countByTagIdAndDeletedFalse(tagId: Long): Long
	
	/**根据类型和用户id分页查询所有未删除收藏。*/
	fun findAllByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long, pageable: Pageable): Page<Collect>
	
	/**根据类型和用户id得到收藏数量。*/
	fun countByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long): Long
	
	/**根据用户id和收藏状态分页查询所有收藏。*/
	fun findAllByUserIdAndDeleted(userId: Long, isDeleted: Boolean, pageable: Pageable): Page<Collect>
	
	/**根据用户id和收藏状态得到收藏数量。*/
	fun countByUserIdAndDeleted(userId: Long, isDeleted: Boolean): Long
	
	/**根据点赞用户id分页查询所有未删除收藏。*/
	fun findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long, pageable: Pageable): Page<Collect>
	
	/**根据点赞用户id得到未删除收藏数量。*/
	fun countByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long): Long
	
	/**检查某一收藏是否已存在。*/
	fun exists(collect: Collect): Boolean
}
