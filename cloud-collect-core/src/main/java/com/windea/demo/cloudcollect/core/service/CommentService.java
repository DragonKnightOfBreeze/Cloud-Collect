package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 评论的服务。
 */
public interface CommentService {
	/**
	 * 创建自己的评论。
	 */
	void create(Long collectId, Comment comment, User sponsorByUser);

	/**
	 * 创建自己的评论，回复某一评论。
	 */
	void reply(Long collectId, Long replyToCommentId, Comment comment, User sponsorByUser);

	/**
	 * 删除自己的评论。
	 */
	void delete(Long id);

	/**
	 * 得到某一评论。
	 */
	Comment get(Long id);

	/**
	 * 分页得到回复某一评论的所有评论。
	 */
	Page<Comment> getReplyByCommentPage(Long id, Pageable pageable);

	/**
	 * 得到回复某一评论的评论数量。
	 */
	Long getReplyByCommentCount(Long id);

	/**
	 * 分页得到所有评论。
	 */
	Page<Comment> findAll(Pageable pageable);

	/**
	 * 分页查询某一收藏的所有评论。
	 */
	Page<Comment> findByCollect(Long collectId, Pageable pageable);
}
