package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Page<Comment> findByCollect_Id(Long collectId, Pageable pageable);

	long countByCollect_Id(Long collectId);

	Page<Comment> findBySponsorByUser_Id(Long sponsorByUserId, Pageable pageable);

	long countBySponsorByUser_Id(Long sponsorByUserId);

	Page<Comment> findByReplyToComment_Id(Long replyToCommentId, Pageable pageable);

	long countByReplyToComment_Id(Long replyToCommentId);
}
