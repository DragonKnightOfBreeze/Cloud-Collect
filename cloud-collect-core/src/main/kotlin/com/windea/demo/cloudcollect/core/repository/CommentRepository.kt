package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CommentRepository : JpaRepository<Comment, Long> {
	fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment>
	
	fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	fun findAllByReplyToCommentId(replyToCommentId: Long, pageable: Pageable): Page<Comment>
	
	fun countByCollectId(collectId: Long): Long
	
	fun countBySponsorByUserId(sponsorByUserId: Long): Long
	
	fun countByReplyToCommentId(replyToCommentId: Long): Long
}
