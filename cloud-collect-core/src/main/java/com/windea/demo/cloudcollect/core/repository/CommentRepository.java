package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Page<Comment> queryByCollect_Id(Long collectId, Pageable pageable);

	Long countByCollect_Id(Long collectId);

	Page<Comment> queryBySponsorByUser_Id(Long sponsorByUserId, Pageable pageable);

	Long countBySponsorByUser_Id(Long sponsorByUserId);

	Page<Comment> queryByReplyToComment_Id(Long replyToCommentId, Pageable pageable);

	Long countByReplyToComment_Id(Long replyToCommentId);
}
