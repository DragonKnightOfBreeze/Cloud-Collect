package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
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
	private val userRepository: UserRepository
) : CollectService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collect: Collect, user: User) {
		val newCollect = collect.copy(
			user = user
		)
		collectRepository.save(newCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun createFrom(collect: Collect, user: User) {
		//点赞别人的收藏
		praise(collect, user)
		
		//从别人的收藏创建新的收藏，需要重置id
		val newCollect = collect.copy(
			id = 0,
			user = user
		)
		collectRepository.save(newCollect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(collect: Collect) {
		collectRepository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun praise(collect: Collect, user: User) {
		collect.praiseByUserList += user
		collectRepository.save(collect)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		collectRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Collect {
		return collectRepository.findByIdOrNull(id)?.lateInit() ?: throw NotFoundException()
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
	override fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContains(categoryName, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagNameContains(tagName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagNameContains(tagName, pageable).map { it.lateInit() }
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
		return collectRepository.findAllByTagNameContainsAndUserId(tagName, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(categoryId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagId(tagId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserId(type, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	override fun existsByNameAndUserId(name: String, userId: Long): Boolean {
		return collectRepository.existsByNameAndUserId(name, userId)
	}
	
	private fun Collect.lateInit() = this.apply {
		praiseByUserCount = userRepository.countByPraiseToCollectId(id)
		commentCount = commentRepository.countByCollectId(id)
	}
	
	
	override fun isPraised(id: Long, user: User): Boolean {
		return userRepository.existsByIdAndFollowByUserListContains(id, user)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getPraiseByUserPage(id: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByPraiseToCollectId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByCollectId(id, pageable)
	}
}
