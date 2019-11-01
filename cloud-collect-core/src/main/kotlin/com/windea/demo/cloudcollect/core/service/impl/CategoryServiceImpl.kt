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
@CacheConfig(cacheNames = ["category"])
class CategoryServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CategoryRepository
) : CategoryService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(category: Category, user: User) {
		val newCategory = category.copy(
			user = user
		)
		categoryRepository.save(newCategory)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, category: Category) {
		val rawCategory = categoryRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCategory.apply {
			name = category.name
			summary = category.summary
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		categoryRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Category {
		return categoryRepository.findByIdOrNull(id)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Category> {
		return categoryRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContains(name: String, pageable: Pageable): Page<Category> {
		return categoryRepository.findAllByNameContains(name, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(userId: Long, name: String, pageable: Pageable): Page<Category> {
		return categoryRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Category> {
		return categoryRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	override fun existsByNameAndUser(name: String, user: User): Boolean {
		return categoryRepository.existsByNameAndUser(name, user)
	}
	
	private fun Category.lateInit() = this.apply {
		collectCount = collectRepository.countByCategoryId(this.id)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(id, pageable)
	}
}
