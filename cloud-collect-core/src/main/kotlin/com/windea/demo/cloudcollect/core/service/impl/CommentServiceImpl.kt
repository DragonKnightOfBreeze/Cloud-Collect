package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["comment"])
open class CommentServiceImpl(
	private val commentRepository: CommentRepository,
	private val collectRepository: CollectRepository
) : CommentService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(collectId: Long, comment: Comment, sponsorByUser: User): Comment {
		comment.collect = collectRepository.findByIdOrNull(collectId) ?: throw NotFoundException()
		comment.sponsorByUser = sponsorByUser
		return commentRepository.save(comment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun reply(collectId: Long, replyToCommentId: Long, comment: Comment, sponsorByUser: User): Comment {
		comment.collect = collectRepository.findByIdOrNull(collectId) ?: throw NotFoundException()
		comment.sponsorByUser = sponsorByUser
		comment.replyToComment = findById(replyToCommentId)
		return commentRepository.save(comment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
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
	override fun countByCollectId(collectId: Long): Long {
		return commentRepository.countByCollectId(collectId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllBySponsorByUserId(sponsorByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countBySponsorByUserId(sponsorByUserId: Long): Long {
		return commentRepository.countBySponsorByUserId(sponsorByUserId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByReplyToCommentId(replyToCommentId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByReplyToCommentId(replyToCommentId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByReplyToCommentId(replyToCommentId: Long): Long {
		return commentRepository.countByReplyToCommentId(replyToCommentId)
	}
}
