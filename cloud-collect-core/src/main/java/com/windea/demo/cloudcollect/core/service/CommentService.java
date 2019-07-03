package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
	void create(Comment comment, Long userId);

	void replyTo(Comment comment, Long userId, Long replyToUserId);

	void delete(Long id);

	Comment get(Long id);

	Page<Comment> queryByCollect(Long collectId, Pageable pageable);

	Page<Comment> queryBySponsorByUser(Long sponsorByUserId, Pageable pageable);

	Page<Comment> queryByReplyToUser(Long replyToUserId, Pageable pageable);
}
