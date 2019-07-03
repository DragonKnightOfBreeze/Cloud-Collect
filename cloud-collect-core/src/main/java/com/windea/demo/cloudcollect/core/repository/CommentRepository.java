package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Page<Comment> queryByCollect(Collect collect, Pageable pageable);

	Page<Comment> queryBySponsorByUser(User user, Pageable pageable);

	Page<Comment> queryByReplyToUser(User user, Pageable pageable);
}
