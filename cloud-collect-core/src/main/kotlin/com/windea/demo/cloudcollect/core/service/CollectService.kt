package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.domain.*

/** 收藏的服务。*/
interface CollectService {
	/** 创建自己的收藏。去除地址的查询参数。*/
	fun create(collect: Collect, user: User): Collect
	
	/** 从别人的收藏创建自己的收藏。默认点赞原始收藏，去除地址的查询参数。*/
	fun createFrom(id: Long, user: User): Collect
	
	/** 删除自己的收藏。不删除数据库中的数据，而是将deleteStatus设为true。*/
	fun delete(id: Long)
	
	/** 修改自己的收藏。名字，概述，分类，标签，类型。*/
	fun modify(id: Long, collect: Collect): Collect
	
	/** 修改自己的收藏的分类。*/
	fun modifyCategory(id: Long, category: CollectCategory): Collect
	
	/** 修改自己的收藏的标签。*/
	fun modifyTags(id: Long, tags: MutableSet<CollectTag>): Collect
	
	/** 修改自己的收藏的类型。*/
	fun modifyType(id: Long, type: CollectType): Collect
	
	/** 点赞某一收藏。*/
	fun praise(id: Long, user: User): Collect
	
	/** 得到某一收藏。*/
	fun get(id: Long): Collect
	
	/** 得到随机收藏。*/
	fun getByRandom(): Collect
	
	/** 分页得到某一收藏的所有点赞用户。*/
	fun getPraiseByUserPage(id: Long, pageable: Pageable): Page<User>
	
	/** 得到某一收藏的点赞用户数量。*/
	fun getPraiseByUserCount(id: Long): Long
	
	/** 分页得到某一收藏的所有评论。*/
	fun getCommentPage(id: Long, pageable: Pageable): Page<Comment>
	
	/** 得到某一收藏的评论数量。*/
	fun getCommentCount(id: Long): Long
	
	/** 分页得到所有收藏。*/
	fun findAll(pageable: Pageable): Page<Collect>
	
	/** 分页查询某一用户的所有已删除/未删除收藏。*/
	fun findByUserAndDeleteStatus(userId: Long, deleteStatus: Boolean, pageable: Pageable): Page<Collect>
	
	/** 根据名字分页模糊查询某一用户的所有收藏。*/
	fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<Collect>
	
	/** 根据分类分页查询某一用户的所有收藏。分类属于唯一用户。*/
	fun findByUserAndCategory(categoryId: Long, pageable: Pageable): Page<Collect>
	
	/** 根据标签分页查询某一用户的所有收藏。标签属于唯一用户。*/
	fun findByUserAndTag(tagId: Long, pageable: Pageable): Page<Collect>
	
	/** 根据类型分页查询某一用户的所有收藏。*/
	fun findByUserAndType(userId: Long, type: CollectType, pageable: Pageable): Page<Collect>
	
	/** 根据名字分页全局查询所有收藏。*/
	fun findByName(name: String, pageable: Pageable): Page<Collect>
	
	/** 检查某一收藏是否已存在。*/
	fun exists(collect: Collect): Boolean
}
