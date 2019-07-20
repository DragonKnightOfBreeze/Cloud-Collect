package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.springframework.data.domain.*

/** 评论的服务。*/
interface CommentService {
	/** 创建自己的评论。*/
	fun create(collectId: Long, comment: Comment, sponsorByUser: User): Comment
	
	/** 创建自己的评论，回复某一评论。*/
	fun reply(collectId: Long, replyToCommentId: Long, comment: Comment, sponsorByUser: User): Comment
	
	/** 删除自己的评论。*/
	fun delete(id: Long)
	
	/** 得到某一评论。*/
	fun get(id: Long): Comment
	
	/** 分页得到回复某一评论的所有评论。*/
	fun getReplyByCommentPage(id: Long, pageable: Pageable): Page<Comment>
	
	/** 得到回复某一评论的评论数量。*/
	fun getReplyByCommentCount(id: Long): Long
	
	/** 分页得到所有评论。*/
	fun findAll(pageable: Pageable): Page<Comment>
	
	/** 分页查询某一收藏的所有评论。*/
	fun findByCollect(collectId: Long, pageable: Pageable): Page<Comment>
}
