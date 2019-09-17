package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

/**评论的仓库。*/
interface CommentRepository : JpaRepository<Comment, Long> {
	fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment>
	
	fun countByCollectId(collectId: Long): Long
	
	fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	fun countBySponsorByUserId(sponsorByUserId: Long): Long
	
	fun findAllByReplyToCommentId(replyToCommentId: Long, pageable: Pageable): Page<Comment>
	
	fun countByReplyToCommentId(replyToCommentId: Long): Long
}
