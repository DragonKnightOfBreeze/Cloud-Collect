package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CommentRepository;
import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository repository;
	private final CollectRepository collectRepository;

	public CommentServiceImpl(CommentRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Override
	public void create(Comment comment, Long userId) {

	}

	@Override
	public void replyTo(Comment comment, Long userId, Long replyToUserId) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Comment get(Long id) {
		return null;
	}

	@Override
	public Page<Comment> queryByCollect(Long collectId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Comment> queryBySponsorByUser(Long sponsorByUserId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Comment> queryByReplyToUser(Long replyToUserId, Pageable pageable) {
		return null;
	}
}
