package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CommentRepository : JpaRepository<Comment, Long> {
	//需要按id倒序排序显示
	fun findAllByCollectIdOrderByIdDesc(collectId: Long, pageable: Pageable): Page<Comment>
	
	//需要按id倒序排序显示
	fun findAllBySponsorByUserIdOrderByIdDesc(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	//需要按id倒序排序显示
	fun findAllByReplyToCommentIdOrderByIdDesc(replyToCommentId: Long, pageable: Pageable): Page<Comment>
	
	fun countByCollectId(collectId: Long): Long
	
	fun countBySponsorByUserId(sponsorByUserId: Long): Long
	
	fun countByReplyToCommentId(replyToCommentId: Long): Long
}
