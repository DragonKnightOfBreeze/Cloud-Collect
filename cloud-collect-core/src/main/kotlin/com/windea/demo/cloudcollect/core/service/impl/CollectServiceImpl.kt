package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.extensions.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
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
			type = collect.type
		)
		return create(savedCollect, user)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		val newCollect = findById(id)
		newCollect.isDeleted = true
		collectRepository.save(newCollect)
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
		return collectRepository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByNameAndUserIdAndDeleted(name: String, userId: Long, isDeleted: Boolean): Collect {
		return collectRepository.findByNameAndUserIdAndDeleted(name, userId, isDeleted).orElseThrow { NotFoundException() }
	}
	
	override fun findByRandom(): Collect {
		val count = collectRepository.count()
		val randomId = RandomExtension.range(1, count)
		return collectRepository.findById(randomId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Collect> {
		return collectRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndDeletedFalse(name: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndDeletedFalse(name, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserIdAndDeletedFalse(name: String, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndUserIdAndDeletedFalse(name, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryIdAndDeletedFalse(categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryIdAndDeletedFalse(categoryId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByCategoryIdAndDeletedFalse(categoryId: Long): Long {
		return collectRepository.countByCategoryIdAndDeletedFalse(categoryId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagIdAndDeletedFalse(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagIdAndDeletedFalse(tagId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByTagIdAndDeletedFalse(tagId: Long): Long {
		return collectRepository.countByTagIdAndDeletedFalse(tagId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserIdAndDeletedFalse(type, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long): Long {
		return collectRepository.countByTypeAndUserIdAndDeletedFalse(type, userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserIdAndDeleted(userId: Long, isDeleted: Boolean, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserIdAndDeleted(userId, isDeleted, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByUserIdAndDeleted(userId: Long, isDeleted: Boolean): Long {
		return collectRepository.countByUserIdAndDeleted(userId, isDeleted)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long): Long {
		return collectRepository.countByPraiseByUserIdAndDeletedFalse(praiseByUserId)
	}
	
	override fun exists(collect: Collect): Boolean {
		val name = collect.name
		val userId = collect.user?.id ?: return false
		val isDeleted = collect.isDeleted
		return collectRepository.existsByNameAndUserIdAndDeleted(name, userId, isDeleted)
	}
}
