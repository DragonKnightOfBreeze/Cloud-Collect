package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*
import kotlin.random.*

@Service
@CacheConfig(cacheNames = ["collect"])
class CollectServiceImpl(
	private val collectRepository: CollectRepository,
	private val commentRepository: CommentRepository,
	private val userRepository: UserRepository,
	private val historyService: HistoryService
) : CollectService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collect: Collect) {
		//链接需要经过处理
		collect.url = collect.url.toNoQueryUrl()
		collect.logoUrl = collect.logoUrl.ifEmpty { collect.url.toLogoUrl() }
		
		collectRepository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun createFrom(collect: Collect, user: User) {
		//首先要点赞别人的收藏
		praise(collect.id, user)
		
		//从别人的收藏创建新的收藏，需要重置id
		val newCollect = collect.copy(
			id = 0,
			user = user
		)
		collectRepository.save(newCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, collect: Collect) {
		val rawCollect = collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCollect.apply {
			name = collect.name
			summary = collect.summary
			url = collect.url.toNoQueryUrl()
			logoUrl = collect.logoUrl.ifEmpty { collect.url.toLogoUrl() }
			category = collect.category
			tags = collect.tags
			type = collect.type
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun praise(id: Long, user: User) {
		val rawCollect = collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCollect.praiseByUsers += user
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun unpraise(id: Long, user: User) {
		val rawCollect = collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCollect.praiseByUsers -= user
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		collectRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Collect {
		return collectRepository.findByIdOrNull(id)?.addHistory()?.lateInit() ?: throw NotFoundException()
	}
	
	override fun findByRandom(): Collect {
		val randomId = Random.nextLong(collectRepository.count()) + 1
		return collectRepository.findByIdOrNull(randomId)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Collect> {
		return collectRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContains(name, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(categoryId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContains(categoryName, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsId(tagId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagNameContains(tagName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContains(tagName, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryNameContainsAndUserId(categoryName: String, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContainsAndUserId(categoryName, userId, pageable)
			.map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagNameContainsAndUserId(tagName: String, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContainsAndUserId(tagName, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserId(type, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByPraiseByUsersId(praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNameContainsAndPraiseByUserId(name: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndPraiseByUsersId(name, praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryNameContainsAndPraiseByUserId(categoryName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContainsAndPraiseByUsersId(categoryName, praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagNameContainsAndPraiseByUserId(tagName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContainsAndPraiseByUsersId(tagName, praiseByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndPraiseByUserId(type: CollectType, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeContainsAndPraiseByUsersId(type, praiseByUserId, pageable)
	}
	
	override fun existsByNameAndUser(name: String, user: User): Boolean {
		return collectRepository.existsByNameAndUser(name, user)
	}
	
	private fun Collect.lateInit() = this.apply {
		isPraised = currentUser?.let { currentUser -> collectRepository.existsByIdAndPraiseByUsers(id, currentUser) }
		praiseByUserCount = userRepository.countByPraiseToCollectsId(id)
		commentCount = commentRepository.countByCollectId(id)
	}
	
	private fun Collect.addHistory() = this.also {
		//尝试为当前用户添加一条浏览记录
		currentUser?.let { currentUser -> historyService.create(History(collect = it, user = currentUser)) }
	}
	
}
