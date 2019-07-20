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
open class CommentServiceImpl(
	private val repository: CommentRepository,
	private val collectRepository: CollectRepository
) : CommentService {
	@Transactional
	override fun create(collectId: Long, comment: Comment, sponsorByUser: User): Comment {
		comment.collect = collectRepository.findById(collectId).orElseThrow { NotFoundException() }
		comment.sponsorByUser = sponsorByUser
		return repository.save(comment)
	}
	
	@Transactional
	override fun reply(collectId: Long, replyToCommentId: Long, comment: Comment, sponsorByUser: User): Comment {
		comment.collect = collectRepository.findById(collectId).orElseThrow { NotFoundException() }
		comment.replyToComment = get(replyToCommentId)
		comment.sponsorByUser = sponsorByUser
		return repository.save(comment)
	}
	
	@Transactional
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Cacheable("comment")
	override fun get(id: Long): Comment {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("comment.replyByCommentPage")
	override fun getReplyByCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return repository.findByReplyToCommentId(id, pageable)
	}
	
	@Cacheable("comment.replyByCommentCount")
	override fun getReplyByCommentCount(id: Long): Long {
		return repository.countByReplyToCommentId(id)
	}
	
	@Cacheable("commentPage")
	override fun findAll(pageable: Pageable): Page<Comment> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("commentPage.byComment")
	override fun findByCollect(collectId: Long, pageable: Pageable): Page<Comment> {
		return repository.findByCollectId(collectId, pageable)
	}
}
