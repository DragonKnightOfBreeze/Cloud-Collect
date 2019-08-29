package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/**评论的服务。*/
interface CommentService {
	/**创建自己的评论。*/
	fun create(collectId: Long, comment: Comment, sponsorByUser: User): Comment
	
	/**创建自己的评论，回复某一评论。*/
	fun reply(collectId: Long, replyToCommentId: Long, comment: Comment, sponsorByUser: User): Comment
	
	/**删除自己的评论。*/
	fun delete(id: Long)
	
	/**根据id得到某一评论。*/
	fun findById(id: Long): Comment
	
	/**得到所有评论。*/
	fun findAll(pageable: Pageable): Page<Comment>
	
	/**根据收藏id查询所有评论。*/
	fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment>
	
	/**根据发起用户id查询所有评论。*/
	fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	
	/**得到回复此评论的评论数量。*/
	fun getReplyByCommentCount(id: Long): Long
	
	/**得到回复此评论的所有评论。*/
	fun getReplyByCommentPage(id: Long, pageable: Pageable): Page<Comment>
}
