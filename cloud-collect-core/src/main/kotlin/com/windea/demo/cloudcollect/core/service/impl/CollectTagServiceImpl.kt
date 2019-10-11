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
@CacheConfig(cacheNames = ["collectTag"])
class CollectTagServiceImpl(
	private val collectRepository: CollectRepository,
	private val tagRepository: CollectTagRepository
) : CollectTagService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(tag: CollectTag, user: User): CollectTag {
		val newTag = tag.copy(
			user = user
		)
		return tagRepository.save(newTag)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(tag: CollectTag) {
		tagRepository.save(tag)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		tagRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): CollectTag {
		return tagRepository.findByIdOrNull(id)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	override fun existsByNameAndUserId(name: String, userId: Long): Boolean {
		return tagRepository.existsByNameAndUserId(name, userId)
	}
	
	private fun CollectTag.lateInit() = this.apply {
		collectCount = collectRepository.countByTagId(id!!)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagId(id, pageable)
	}
}
