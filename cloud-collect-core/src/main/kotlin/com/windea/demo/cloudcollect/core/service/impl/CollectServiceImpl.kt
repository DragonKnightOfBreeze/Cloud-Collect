package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.extension.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collect"])
open class CollectServiceImpl(
	private val repository: CollectRepository
) : CollectService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collect: Collect, user: User): Collect {
		collect.url = collect.url.toUrlInfo().fullPath
		collect.logoUrl = collect.logoUrl.toUrlInfo().fullPath
		collect.user = user
		return repository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun createFrom(id: Long, user: User): Collect {
		praise(id, user)
		
		//从别人的收藏创建新的收藏，需要先将id设为null
		val collect = findById(id)
		collect.id = null
		return create(collect, user)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		val collect = findById(id)
		collect.isDeleted = true
		repository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, collect: Collect): Collect {
		val rawCollect = findById(id)
		rawCollect.name = collect.name
		rawCollect.summary = collect.summary
		rawCollect.category = collect.category
		rawCollect.tags = collect.tags
		rawCollect.type = collect.type
		return repository.save(rawCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyCategory(id: Long, category: CollectCategory): Collect {
		val rawCollect = findById(id)
		rawCollect.category = category
		return repository.save(rawCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyTags(id: Long, tags: MutableSet<CollectTag>): Collect {
		val rawCollect = findById(id)
		rawCollect.tags = tags
		return repository.save(rawCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modifyType(id: Long, type: CollectType): Collect {
		val rawCollect = findById(id)
		rawCollect.type = type
		return repository.save(rawCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun praise(id: Long, user: User): Collect {
		val collect = findById(id)
		val praiseByUserList = collect.praiseByUserList
		praiseByUserList.add(user)
		collect.praiseByUserList = praiseByUserList
		return repository.save(collect)
		
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Collect {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByNameAndUserIdAndDeleted(name: String, userId: Long, isDeleted: Boolean): Collect {
		return repository.findByNameAndUserIdAndDeleted(name, userId, isDeleted).orElseThrow { NotFoundException() }
	}
	
	override fun findByRandom(): Collect {
		val count = repository.count()
		val randomId = RandomExtension.range(1, count)
		return repository.findById(randomId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Collect> {
		return repository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndDeletedFalse(name: String, pageable: Pageable): Page<Collect> {
		return repository.findAllByNameContainsAndDeletedFalse(name, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserIdAndDeletedFalse(name: String, userId: Long,
		pageable: Pageable): Page<Collect> {
		return repository.findAllByNameContainsAndUserIdAndDeletedFalse(name, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryIdAndDeletedFalse(categoryId: Long, pageable: Pageable): Page<Collect> {
		return repository.findAllByCategoryIdAndDeletedFalse(categoryId, pageable)
	}
	
	override fun countByCategoryIdAndDeletedFalse(categoryId: Long): Long {
		return repository.countByCategoryIdAndDeletedFalse(categoryId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagIdAndDeletedFalse(tagId: Long, pageable: Pageable): Page<Collect> {
		return repository.findAllByTagIdAndDeletedFalse(tagId, pageable)
	}
	
	override fun countByTagIdAndDeletedFalse(tagId: Long): Long {
		return repository.countByTagIdAndDeletedFalse(tagId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long,
		pageable: Pageable): Page<Collect> {
		return repository.findAllByTypeAndUserIdAndDeletedFalse(type, userId, pageable)
	}
	
	override fun countByTypeAndUserIdAndDeletedFalse(type: CollectType, userId: Long): Long {
		return repository.countByTypeAndUserIdAndDeletedFalse(type, userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserIdAndDeleted(userId: Long, isDeleted: Boolean, pageable: Pageable): Page<Collect> {
		return repository.findAllByUserIdAndDeleted(userId, isDeleted, pageable)
	}
	
	override fun countByUserIdAndDeleted(userId: Long, isDeleted: Boolean): Long {
		return repository.countByUserIdAndDeleted(userId, isDeleted)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return repository.findAllByPraiseByUserIdAndDeletedFalse(praiseByUserId, pageable)
	}
	
	override fun countByPraiseByUserIdAndDeletedFalse(praiseByUserId: Long): Long {
		return repository.countByPraiseByUserIdAndDeletedFalse(praiseByUserId)
	}
	
	override fun exists(collect: Collect): Boolean {
		val name = collect.name
		val userId = collect.user.id
		val isDeleted = collect.isDeleted
		return userId != null && repository.existsByNameAndUserIdAndDeleted(name, userId, isDeleted)
	}
}
