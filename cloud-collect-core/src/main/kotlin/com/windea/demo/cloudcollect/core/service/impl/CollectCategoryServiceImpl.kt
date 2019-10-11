package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collectCategory"])
class CollectCategoryServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CollectCategoryRepository
) : CollectCategoryService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(category: CollectCategory, user: User): CollectCategory {
		val newCategory = category.copy(
			user = user
		)
		return categoryRepository.save(newCategory)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(category: CollectCategory) {
		categoryRepository.save(category)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		categoryRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): CollectCategory {
		return categoryRepository.findByIdOrNull(id)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	override fun existsByNameAndUserId(name: String, userId: Long): Boolean {
		return categoryRepository.existsByNameAndUserId(name, userId)
	}
	
	private fun CollectCategory.lateInit() = this.apply {
		collectCount = collectRepository.countByCategoryId(this.id!!)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(id, pageable)
	}
}
