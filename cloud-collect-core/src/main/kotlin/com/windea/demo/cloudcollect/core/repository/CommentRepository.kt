package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CommentRepository : JpaRepository<Comment, Long> {
	fun findByCollectId(collectId: Long, pageable: Pageable): Page<Comment>
	
	fun countByCollectId(collectId: Long): Long
	
	fun findBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	fun countBySponsorByUserId(sponsorByUserId: Long): Long
	
	fun findByReplyToCommentId(replyToCommentId: Long, pageable: Pageable): Page<Comment>
	
	fun countByReplyToCommentId(replyToCommentId: Long): Long
}
