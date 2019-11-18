package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

interface CommentService {
	/**创建自己的评论。*/
	fun create(comment: Comment)
	
	/**创建自己的评论，回复某一评论。*/
	fun reply(comment: Comment)
	
	/**删除自己的评论。*/
	fun deleteById(id: Long)
	
	/**根据id得到某一评论。*/
	fun findById(id: Long): Comment
	
	/**得到所有评论。*/
	fun findAll(pageable: Pageable): Page<Comment>
	
	/**根据收藏id查询所有评论。*/
	fun findAllByCollectId(collectId: Long, pageable: Pageable): Page<Comment>
	
	/**根据发起用户id查询所有评论。*/
	fun findAllBySponsorByUserId(sponsorByUserId: Long, pageable: Pageable): Page<Comment>
	
	/**得到回复此评论的所有评论。*/
	fun getReplyByCommentPage(id: Long, pageable: Pageable): Page<Comment>
}
