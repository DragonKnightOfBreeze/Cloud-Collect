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
@CacheConfig(cacheNames = ["collectTag"])
open class CollectTagServiceImpl(
	private val repository: CollectTagRepository
) : CollectTagService {
	@Transactional
	@CachePut
	override fun create(tag: CollectTag, user: User): CollectTag {
		tag.user = user
		return repository.save(tag)
	}
	
	@Transactional
	@CacheEvict
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	@CachePut
	override fun modify(id: Long, tag: CollectTag): CollectTag {
		val rawTag = findById(id)
		rawTag.name = tag.name
		rawTag.summary = tag.summary
		return repository.save(rawTag)
	}
	
	@Cacheable
	override fun findById(id: Long): CollectTag {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable
	override fun findByNameAndUserId(name: String, userId: Long): CollectTag {
		return repository.findByNameAndUserId(name, userId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable
	override fun findAll(pageable: Pageable): Page<CollectTag> {
		return repository.findAll(pageable)
	}
	
	@Cacheable
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag> {
		return repository.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@Cacheable
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag> {
		return repository.findAllByUserId(userId, pageable)
	}
	
	override fun countByUserId(userId: Long): Long {
		return repository.countByUserId(userId)
	}
	
	override fun exists(tag: CollectTag): Boolean {
		val name = tag.name
		val userId = tag.user.id
		return userId != null && repository.existsByNameAndUserId(name, userId)
	}
}
