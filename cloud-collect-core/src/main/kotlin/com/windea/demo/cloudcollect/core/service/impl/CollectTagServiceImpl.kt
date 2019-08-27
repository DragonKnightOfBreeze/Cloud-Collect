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
	private val tagRepository: CollectTagRepository
) : CollectTagService {
	@Transactional
	@CachePut
	override fun create(tag: CollectTag, user: User): CollectTag {
		tag.user = user
		return tagRepository.save(tag)
	}
	
	@Transactional
	@CacheEvict
	override fun delete(id: Long) {
		tagRepository.deleteById(id)
	}
	
	@Transactional
	@CachePut
	override fun modify(id: Long, tag: CollectTag): CollectTag {
		val savedTag = findById(id)
		savedTag.name = tag.name
		savedTag.summary = tag.summary
		return tagRepository.save(savedTag)
	}
	
	@Cacheable
	override fun findById(id: Long): CollectTag {
		return tagRepository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable
	override fun findByNameAndUserId(name: String, userId: Long): CollectTag {
		return tagRepository.findByNameAndUserId(name, userId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable
	override fun findAll(pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAll(pageable)
	}
	
	@Cacheable
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@Cacheable
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<CollectTag> {
		return tagRepository.findAllByUserId(userId, pageable)
	}
	
	override fun countByUserId(userId: Long): Long {
		return tagRepository.countByUserId(userId)
	}
	
	override fun exists(tag: CollectTag): Boolean {
		val name = tag.name
		val userId = tag.user?.id ?: return false
		return tagRepository.existsByNameAndUserId(name, userId)
	}
}
