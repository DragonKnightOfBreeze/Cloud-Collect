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
@CacheConfig(cacheNames = ["tag"])
class TagServiceImpl(
	private val collectRepository: CollectRepository,
	private val tagRepository: TagRepository
) : TagService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(tag: Tag) {
		tagRepository.save(tag)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, tag: Tag) {
		val rawTag = tagRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawTag.apply {
			name = tag.name
			summary = tag.summary
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		tagRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Tag {
		return tagRepository.findByIdOrNull(id)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Tag> {
		return tagRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContains(name: String, pageable: Pageable): Page<Tag> {
		return tagRepository.findAllByNameContains(name, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Tag> {
		return tagRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Tag> {
		return tagRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	override fun existsByName(name: String): Boolean {
		return tagRepository.existsByName(name)
	}
	
	private fun Tag.lateInit() = this.apply {
		collectCount = collectRepository.countByTagsId(id)
	}
}
