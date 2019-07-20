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
open class CollectTagServiceImpl(
	private val repository: CollectTagRepository,
	private val collectRepository: CollectRepository
) : CollectTagService {
	@Transactional
	override fun create(tag: CollectTag, user: User): CollectTag {
		tag.user = user
		return repository.save(tag)
	}
	
	@Transactional
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	override fun modify(id: Long, tag: CollectTag): CollectTag {
		val rawTag = get(id)
		rawTag.name = tag.name
		rawTag.summary = tag.summary
		return repository.save(rawTag)
	}
	
	@Cacheable("collectTag")
	override fun get(id: Long): CollectTag {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("collectTag.collectPage")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findByTagIdAndDeleteStatusFalse(id, pageable)
	}
	
	@Cacheable("collectTag.collectCount")
	override fun getCollectCount(id: Long): Long {
		return collectRepository.countByTagIdAndDeleteStatusFalse(id)
	}
	
	@Cacheable("collectTagPage")
	override fun findAll(pageable: Pageable): Page<CollectTag> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("collectTagPage.byUser")
	override fun findByUser(userId: Long, pageable: Pageable): Page<CollectTag> {
		return repository.findByUserId(userId, pageable)
	}
	
	@Cacheable("collectTagPage.byUserAndName")
	override fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<CollectTag> {
		return repository.findByUserIdAndNameContains(userId, name, pageable)
	}
	
	override fun exists(tag: CollectTag): Boolean {
		val userId = tag.user.id ?: return false
		val name = tag.name
		return repository.existsByUserIdAndName(userId, name)
	}
}
