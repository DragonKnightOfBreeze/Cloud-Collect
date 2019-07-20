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
open class CollectCategoryServiceImpl(
	private val repository: CollectCategoryRepository,
	private val collectRepository: CollectRepository
) : CollectCategoryService {
	@Transactional
	override fun create(category: CollectCategory, user: User): CollectCategory {
		category.user = user
		return repository.save(category)
	}
	
	@Transactional
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	override fun modify(id: Long, category: CollectCategory): CollectCategory {
		val rawCategory = get(id)
		rawCategory.name = category.name
		rawCategory.summary = category.summary
		return repository.save(rawCategory)
	}
	
	@Cacheable("collectCategory")
	override fun get(id: Long): CollectCategory {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("collectCategory.collectPage")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findByCategoryIdAndDeleteStatusFalse(id, pageable)
	}
	
	@Cacheable("collectCategory.collectCount")
	override fun getCollectCount(id: Long): Long {
		return collectRepository.countByCategoryIdAndDeleteStatusFalse(id)
	}
	
	@Cacheable("collectCategoryPage")
	override fun findAll(pageable: Pageable): Page<CollectCategory> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("collectCategoryPage.byUser")
	override fun findByUser(userId: Long, pageable: Pageable): Page<CollectCategory> {
		return repository.findByUserId(userId, pageable)
	}
	
	@Cacheable("collectCategoryPage.byUserAndName")
	override fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<CollectCategory> {
		return repository.findByUserIdAndNameContains(userId, name, pageable)
	}
	
	override fun exists(category: CollectCategory): Boolean {
		val userId = category.user.id ?: return false
		val name = category.name
		return repository.existsByUserIdAndName(userId, name)
	}
}
