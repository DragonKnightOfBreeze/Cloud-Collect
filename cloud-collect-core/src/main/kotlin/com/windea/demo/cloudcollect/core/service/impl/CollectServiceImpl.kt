@file:Suppress("DuplicatedCode")

package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import kotlinx.coroutines.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*
import kotlin.random.*

@Service
@CacheConfig(cacheNames = ["collect"], keyGenerator = "methodNameArgsKeyGenerator")
class CollectServiceImpl(
	private val collectRepository: CollectRepository,
	private val commentRepository: CommentRepository,
	private val userRepository: UserRepository,
	private val historyService: HistoryService,
	private val noticeService: NoticeService
) : CollectService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collect: Collect) {
		val currentUser = currentUser!!
		//链接需要经过处理
		collect.url = collect.url.toNoQueryUrl()
		collect.logoUrl = collect.logoUrl.ifEmpty { collect.url.toLogoUrl() }
		
		collectRepository.save(collect).addCreateNotice(currentUser)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun fork(collect: Collect) {
		val currentUser = currentUser!!
		//首先要点赞别人的收藏
		val rawCollect = collectRepository.findByIdOrNull(collect.id) ?: throw NotFoundException()
		rawCollect.praiseByUsers += currentUser
		
		//从别人的收藏创建新的收藏，需要重置id
		val newCollect = collect.copy(id = 0, user = currentUser)
		collectRepository.save(newCollect).addForkNotice(rawCollect, currentUser)
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
	override fun praise(id: Long) {
		val currentUser = currentUser!!
		val rawCollect = collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCollect.praiseByUsers += currentUser
		rawCollect.addPraiseNotice(currentUser)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun unpraise(id: Long) {
		val currentUser = currentUser!!
		val rawCollect = collectRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawCollect.praiseByUsers -= currentUser
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		collectRepository.deleteById(id)
	}
	
	@Cacheable
	override fun findById(id: Long): Collect {
		return collectRepository.findByIdOrNull(id)?.lateInit()?.also {
			currentUser?.let { currentUser -> it.addHistory(currentUser) }
		} ?: throw NotFoundException()
	}
	
	override fun findByRandom(): Collect {
		val randomId = Random.nextLong(collectRepository.count()) + 1
		return collectRepository.findByIdOrNull(randomId)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable
	override fun findAll(pageable: Pageable): Page<Collect> {
		return collectRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByNameContains(name: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContains(name, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByCategoryId(categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryId(categoryId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByCategoryName(categoryName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryName(categoryName, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByCategoryNameContains(categoryName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContains(categoryName, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByTagId(tagId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsId(tagId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByTagName(tagName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsName(tagName, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByTagNameContains(tagName: String, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContains(tagName, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByNameContainsAndUserId(name: String, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndUserId(name, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByCategoryNameContainsAndUserId(categoryName: String, userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContainsAndUserId(categoryName, userId, pageable)
			.map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByTagNameContainsAndUserId(tagName: String, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContainsAndUserId(tagName, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByTypeAndUserId(type: CollectType, userId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTypeAndUserId(type, userId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByPraiseByUserId(praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByPraiseByUsersId(praiseByUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByNameContainsAndPraiseByUserId(name: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByNameContainsAndPraiseByUsersId(name, praiseByUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByCategoryNameContainsAndPraiseByUserId(categoryName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByCategoryNameContainsAndPraiseByUsersId(categoryName, praiseByUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByTagNameContainsAndPraiseByUserId(tagName: String, praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByTagsNameContainsAndPraiseByUsersId(tagName, praiseByUserId, pageable)
	}
	
	@Cacheable
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
	
	//DONE 使用协程实现以下代码，注意这时当前用户已被清空
	
	//尝试为当前用户添加一条浏览记录
	private fun Collect.addHistory(currentUser: User) = GlobalScope.launch {
		val collect = this@addHistory
		val history = History(
			collect = collect,
			user = currentUser
		)
		historyService.create(history)
	}
	
	//尝试为当前用户（以及他的所有粉丝用户）添加一条创建收藏通知
	private fun Collect.addCreateNotice(currentUser: User) = GlobalScope.launch {
		val collect = this@addCreateNotice
		val notice = Notice(
			title = "${currentUser.nickname}刚刚创建了一条收藏",
			//language=HTML
			content = """
				<div>
				  <a href="/profile/${currentUser.id}">${currentUser.nickname}</a>
				  刚刚创建了一条收藏：
				  <a href="/collects/${collect.id}">${collect.name}</a>
				</div>
			""".trimIndent(),
			type = NoticeType.ACCOUNT,
			user = currentUser
		)
		noticeService.create(notice)
		for(followByUser in currentUser.followByUsers) {
			noticeService.create(notice.copy(user = followByUser))
		}
	}
	
	//尝试为当前用户（以及他的所有粉丝用户）添加一条拷贝收藏通知
	private fun Collect.addForkNotice(rawCollect: Collect, currentUser: User) = GlobalScope.launch {
		val collect = this@addForkNotice
		val notice = Notice(
			title = "${currentUser.nickname}刚刚拷贝了一条收藏",
			//language=HTML
			content = """
				<div>
				  <a href="/profile/${currentUser.id}">${currentUser.nickname}</a>
				  刚刚从收藏：
				  <a href="/collects/${rawCollect.id}">${rawCollect.name}</a>
				</div>
				<div>
				  拷贝了一条收藏：
				  <a href="/collects/${collect.id}">${collect.name}</a>
				</div>
			""".trimIndent(),
			type = NoticeType.ACCOUNT,
			user = currentUser
		)
		noticeService.create(notice)
		for(followByUser in currentUser.followByUsers) {
			noticeService.create(notice.copy(user = followByUser))
		}
	}
	
	//尝试为当前用户（以及他的所有粉丝用户）添加一条点赞收藏通知
	private fun Collect.addPraiseNotice(currentUser: User) = GlobalScope.launch {
		val collect = this@addPraiseNotice
		val notice = Notice(
			title = "${currentUser.nickname}刚刚点赞了一条收藏",
			//language=HTML
			content = """
				<div>
				  <a href="/profile/${currentUser.id}">${currentUser.nickname}</a>
				  刚刚点赞了一条收藏：
				  <a href="/collects/${collect.id}">${collect.name}</a>
				</div>
			""".trimIndent(),
			type = NoticeType.ACCOUNT,
			user = currentUser
		)
		noticeService.create(notice)
		for(followByUser in currentUser.followByUsers) {
			noticeService.create(notice.copy(user = followByUser))
		}
	}
}
