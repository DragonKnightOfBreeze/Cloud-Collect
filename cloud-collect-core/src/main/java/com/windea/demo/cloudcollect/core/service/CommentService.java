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
	void replyTo(Comment comment, Collect collect, Comment replyToComment, User sponsorByUser);

	/**
	 * 删除自己的评论。
	 */
	void delete(Long id);

	/**
	 * 得到某一评论。
	 */
	Comment get(Long id);

	/**
	 * 根据收藏分页查询所有评论。
	 */
	Page<Comment> queryByCollect(Long collectId, Pageable pageable);

	/**
	 * 根据回复评论分页查询所有评论。
	 */
	Page<Comment> queryByReplyToComment(Long replyToCommentId, Pageable pageable);

	/**
	 * 根据发起用户分页查询所有评论。
	 */
	Page<Comment> queryBySponsorByUser(Long sponsorByUserId, Pageable pageable);

	/**
	 * TODO 创建或回复评论时通知好友。
	 */
	void noticeFriends();
}
