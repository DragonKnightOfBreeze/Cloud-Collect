package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.extensions.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collect"])
class CollectServiceImpl(
	private val collectRepository: CollectRepository,
	private val commentRepository: CommentRepository,
	private val userRepository: UserRepository
) : CollectService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collect: Collect, user: User): Collect {
		collect.user = user
		return collectRepository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun createFrom(id: Long, user: User): Collect {
		//点赞别人的收藏
		praise(id, user)
		
		//从别人的收藏创建新的收藏
		val collect = findById(id)
		val savedCollect = Collect(
			name = collect.name,
			summary = collect.summary,
			url = collect.url,
			logoUrl = collect.logoUrl,
			category = collect.category,
			tags = collect.tags,
			type = collect.type,
			user = collect.user
		)
		return collectRepository.save(savedCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		collectRepository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, collect: Collect): Collect {
		val savedCollect = findById(id)
		savedCollect.name = collect.name
		savedCollect.summary = collect.summary
		savedCollect.url = collect.url
		savedCollect.logoUrl = collect.logoUrl
		savedCollect.category = collect.category
		savedCollect.tags = collect.tags
		savedCollect.type = collect.type
		return collectRepository.save(savedCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyCategory(id: Long, category: CollectCategory): Collect {
		val savedCollect = findById(id)
		savedCollect.category = category
		return collectRepository.save(savedCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyTags(id: Long, tags: MutableSet<CollectTag>): Collect {
		val savedCollect = findById(id)
		savedCollect.tags = tags
		return collectRepository.save(savedCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyType(id: Long, type: CollectType): Collect {
		val savedCollect = findById(id)
		savedCollect.type = type
		return collectRepository.save(savedCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun praise(id: Long, user: User): Collect {
		val savedCollect = findById(id)
		savedCollect.praiseByUserList += user
		return collectRepository.save(savedCollect)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Collect {
		return collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByNameAndUserId(name: String, userId: Long): Collect {
		return collectRepository.findByNameAndUserId(name, userId) ?: throw NotFoundException()
	}
	
	override fun findByRandom(): Collect {
		val randomId = RandomExtension.range(1, collectRepository.count())
		return findById(randomId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Collect> {
		return collectRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContains(name, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(name: String, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(categoryId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagId(tagId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserId(type: CollectType, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(userId, pageable)
	}
	
	override fun existsByNameAndUserId(name: String, userId: Long): Boolean {
		return collectRepository.existsByNameAndUserId(name, userId)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getPraiseByUserCount(id: Long): Long {
		return userRepository.countByPraiseToCollectId(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getCommentCount(id: Long): Long {
		return commentRepository.countByCollectId(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getPraiseByUserPage(id: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByPraiseToCollectId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByCollectId(id, pageable)
	}
}
