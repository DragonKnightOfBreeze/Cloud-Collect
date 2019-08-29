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
open class CollectServiceImpl(
	private val collectRepository: CollectRepository
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
	override fun countByCategoryId(categoryId: Long): Long {
		return collectRepository.countByCategoryId(categoryId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagId(tagId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByTagId(tagId: Long): Long {
		return collectRepository.countByTagId(tagId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserId(type: CollectType, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByTypeAndUserId(type: CollectType, userId: Long): Long {
		return collectRepository.countByTypeAndUserId(type, userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByUserId(userId: Long): Long {
		return collectRepository.countByUserId(userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByPraiseByUserId(praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByPraiseByUserId(praiseByUserId: Long): Long {
		return collectRepository.countByPraiseByUserId(praiseByUserId)
	}
	
	override fun exists(collect: Collect): Boolean {
		val name = collect.name
		val userId = collect.user.id ?: return false
		return collectRepository.existsByNameAndUserId(name, userId)
	}
}
