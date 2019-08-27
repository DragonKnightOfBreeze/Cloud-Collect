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
	private val categoryRepository: CollectCategoryRepository
) : CollectCategoryService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(category: CollectCategory, user: User): CollectCategory {
		category.user = user
		return categoryRepository.save(category)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		categoryRepository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, category: CollectCategory): CollectCategory {
		val savedCategory = findById(id)
		savedCategory.name = category.name
		savedCategory.summary = category.summary
		return categoryRepository.save(savedCategory)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): CollectCategory {
		return categoryRepository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByNameAndUserId(name: String, userId: Long): CollectCategory {
		return categoryRepository.findByNameAndUserId(name, userId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAllByUserId(userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByUserId(userId: Long): Long {
		return categoryRepository.countByUserId(userId)
	}
	
	override fun exists(category: CollectCategory): Boolean {
		val name = category.name
		val userId = category.user.id ?: return false
		return categoryRepository.existsByNameAndUserId(name, userId)
	}
}
