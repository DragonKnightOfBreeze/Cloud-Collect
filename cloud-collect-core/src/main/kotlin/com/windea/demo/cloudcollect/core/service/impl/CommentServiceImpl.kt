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
@CacheConfig(cacheNames = ["comment"])
class CommentServiceImpl(
	private val commentRepository: CommentRepository
) : CommentService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(comment: Comment, sponsorByUser: User) {
		val newComment = comment.copy(
			sponsorByUser = sponsorByUser
		)
		commentRepository.save(newComment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun reply(replyToCommentId: Long, comment: Comment, sponsorByUser: User) {
		val newComment = comment.copy(
			replyToComment = commentRepository.findByIdOrNull(replyToCommentId) ?: throw NotFoundException(),
			sponsorByUser = sponsorByUser
		)
		commentRepository.save(newComment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		commentRepository.deleteById(id)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Comment {
		return commentRepository.findByIdOrNull(id) ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Comment> {
		return commentRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByCollectId(collectId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllBySponsorByUserId(sponsorByUserId, pageable)
	}
	
	private fun Comment.lateInit() = this.apply {
		replyByCommentCount = commentRepository.countByReplyToCommentId(id)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getReplyByCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByReplyToCommentId(id, pageable)
	}
}
