package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.extension.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
open class CollectServiceImpl(
	private val repository: CollectRepository,
	private val userRepository: UserRepository,
	private val commentRepository: CommentRepository
) : CollectService {
	@Transactional
	override fun create(collect: Collect, user: User): Collect {
		collect.url = collect.url.toUrlInfo().fullPath
		collect.logoUrl = collect.logoUrl.toUrlInfo().fullPath
		collect.user = user
		return repository.save(collect)
	}
	
	@Transactional
	override fun createFrom(id: Long, user: User): Collect {
		praise(id, user)
		
		//从别人的收藏创建新的收藏，需要先将id设为null
		val collect = get(id)
		collect.id = null
		return create(collect, user)
	}
	
	@Transactional
	override fun delete(id: Long) {
		val collect = get(id)
		collect.deleteStatus = true
		repository.save(collect)
	}
	
	@Transactional
	override fun modify(id: Long, collect: Collect): Collect {
		val rawCollect = get(id)
		rawCollect.name = collect.name
		rawCollect.summary = collect.summary
		rawCollect.category = collect.category
		rawCollect.tags = collect.tags
		rawCollect.type = collect.type
		return repository.save(rawCollect)
	}
	
	@Transactional
	override fun modifyCategory(id: Long, category: CollectCategory): Collect {
		val rawCollect = get(id)
		rawCollect.category = category
		return repository.save(rawCollect)
	}
	
	@Transactional
	override fun modifyTags(id: Long, tags: MutableSet<CollectTag>): Collect {
		val rawCollect = get(id)
		rawCollect.tags = tags
		return repository.save(rawCollect)
	}
	
	@Transactional
	override fun modifyType(id: Long, type: CollectType): Collect {
		val rawCollect = get(id)
		rawCollect.type = type
		return repository.save(rawCollect)
	}
	
	@Transactional
	override fun praise(id: Long, user: User): Collect {
		val collect = get(id)
		val praiseByUserList = collect.praiseByUserList
		praiseByUserList.add(user)
		collect.praiseByUserList = praiseByUserList
		return repository.save(collect)
		
	}
	
	@Cacheable("collect")
	override fun get(id: Long): Collect {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	override fun getByRandom(): Collect {
		val count = repository.count()
		val randomId = RandomExtension.range(1, count)
		return repository.findById(randomId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("collect.praiseByUserPage")
	override fun getPraiseByUserPage(id: Long, pageable: Pageable): Page<User> {
		return userRepository.findByPraiseToCollectId(id, pageable)
	}
	
	@Cacheable("collect.praiseByUserCount")
	override fun getPraiseByUserCount(id: Long): Long {
		return userRepository.countByPraiseToCollectId(id)
	}
	
	@Cacheable("collect.commentPage")
	override fun getCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findByCollectId(id, pageable)
	}
	
	@Cacheable("collect.commentCount")
	override fun getCommentCount(id: Long): Long {
		return commentRepository.countByCollectId(id)
	}
	
	@Cacheable("collectPage")
	override fun findAll(pageable: Pageable): Page<Collect> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("collectPage.byUserAndDeleteStatus")
	override fun findByUserAndDeleteStatus(userId: Long, deleteStatus: Boolean, pageable: Pageable): Page<Collect> {
		return repository.findByUserIdAndDeleteStatus(userId, deleteStatus, pageable)
	}
	
	@Cacheable("collectPage.byUserAndName")
	override fun findByUserAndName(userId: Long, name: String, pageable: Pageable): Page<Collect> {
		return repository.findByUserIdAndNameContainsAndDeleteStatusFalse(userId, name, pageable)
	}
	
	@Cacheable("collectPage.byUserAndCategory")
	override fun findByUserAndCategory(categoryId: Long, pageable: Pageable): Page<Collect> {
		return repository.findByCategoryIdAndDeleteStatusFalse(categoryId, pageable)
	}
	
	@Cacheable("collectPage.byUserAndTag")
	override fun findByUserAndTag(tagId: Long, pageable: Pageable): Page<Collect> {
		return repository.findByTagIdAndDeleteStatusFalse(tagId, pageable)
	}
	
	@Cacheable("collectPage.byUserAndType")
	override fun findByUserAndType(userId: Long, type: CollectType, pageable: Pageable): Page<Collect> {
		return repository.findByUserIdAndTypeAndDeleteStatusFalse(userId, type, pageable)
	}
	
	@Cacheable("collectPage.byName")
	override fun findByName(name: String, pageable: Pageable): Page<Collect> {
		return repository.findByNameContainsAndDeleteStatusFalse(name, pageable)
	}
	
	override fun exists(collect: Collect): Boolean {
		val userId = collect.user.id ?: return false
		val name = collect.name
		return repository.existsByUserIdAndName(userId, name)
	}
}
