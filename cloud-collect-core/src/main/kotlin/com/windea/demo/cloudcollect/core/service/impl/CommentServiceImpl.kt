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
@CacheConfig(cacheNames = ["comment"], keyGenerator = "methodNameArgsKeyGenerator")
class CommentServiceImpl(
	private val commentRepository: CommentRepository
) : CommentService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(comment: Comment) {
		commentRepository.save(comment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun reply(comment: Comment) {
		commentRepository.save(comment)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		commentRepository.deleteById(id)
	}
	
	@Cacheable
	override fun findById(id: Long): Comment {
		return commentRepository.findByIdOrNull(id) ?: throw NotFoundException()
	}
	
	@Cacheable
	override fun findAll(pageable: Pageable): Page<Comment> {
		return commentRepository.findAll(pageable)
	}
	
	@Cacheable
	override fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByCollectIdOrderByIdDesc(collectId, pageable)
	}
	
	@Cacheable
	override fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllBySponsorByUserIdOrderByIdDesc(sponsorByUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByReplyToCommentId(replyToCommentId: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllByReplyToCommentIdOrderByIdDesc(replyToCommentId, pageable)
	}
	
	private fun Comment.lateInit() = this.apply {
		replyToCommentCount = commentRepository.countByReplyToCommentId(id)
	}
}
