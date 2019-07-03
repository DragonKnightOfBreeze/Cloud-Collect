package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Page<Comment> queryByCollect_Id(Long collectId, Pageable pageable);

	Page<Comment> queryBySponsorByUser_Id(Long sponsorByUserId, Pageable pageable);

	Page<Comment> queryByReplyToUser_Id(Long replyToUserId, Pageable pageable);
}
