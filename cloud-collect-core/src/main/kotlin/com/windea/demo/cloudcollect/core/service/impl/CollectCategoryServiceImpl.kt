package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collectCategory"])
open class CollectCategoryServiceImpl(
	private val repository: CollectCategoryRepository
) : CollectCategoryService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(category: CollectCategory, user: User): CollectCategory {
		category.user = user
		return repository.save(category)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, category: CollectCategory): CollectCategory {
		val rawCategory = findById(id)
		rawCategory.name = category.name
		rawCategory.summary = category.summary
		return repository.save(rawCategory)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): CollectCategory {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByNameAndUserId(name: String, userId: Long): CollectCategory {
		return repository.findByNameAndUserId(name, userId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<CollectCategory> {
		return repository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<CollectCategory> {
		return repository.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory> {
		return repository.findAllByUserId(userId, pageable)
	}
	
	override fun countByUserId(userId: Long): Long {
		return repository.countByUserId(userId)
	}
	
	override fun exists(category: CollectCategory): Boolean {
		val name = category.name
		val userId = category.user.id
		return userId != null && repository.existsByNameAndUserId(name, userId)
	}
}
