package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 评论的服务。
 */
public interface CommentService {
	/**
	 * 创建自己的评论。
	 */
	void create(Comment comment, Collect collect, User sponsorByUser);

	/**
	 * 创建自己的评论，回复某一评论。
	 */
	void reply(Comment comment, Collect collect, Comment replyToComment, User sponsorByUser);

	/**
	 * 删除自己的评论。
	 */
	void delete(Long id);

	/**
	 * 得到某一评论。
	 */
	Comment get(Long id);

	/**
	 * 得到回复某一评论的所有评论。
	 */
	Page<Comment> getReplyByCommentPage(Long id, Pageable pageable);

	/**
	 * 得到回复某一评论的评论数量。
	 */
	Long getReplyByCommentCount(Long id);

	/**
	 * 查询某一收藏的所有评论。
	 */
	Page<Comment> queryByCollect(Long collectId, Pageable pageable);
}
