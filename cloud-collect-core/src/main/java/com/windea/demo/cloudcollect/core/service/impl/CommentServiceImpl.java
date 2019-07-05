package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.exception.NotImplementedException;
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
	public void create(Comment comment, Collect collect, User sponsorByUser) {
		comment.setCollect(collect);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);

		noticeFriends();
	}

	@Override
	public void replyTo(Comment comment, Collect collect, Comment replyToComment, User sponsorByUser) {
		comment.setCollect(collect);
		comment.setReplyToComment(replyToComment);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);

		noticeFriends();
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Comment get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Comment> queryByCollect(Long collectId, Pageable pageable) {
		return repository.queryByCollect_Id(collectId, pageable);
	}

	@Override
	public Page<Comment> queryByReplyToComment(Long replyToCommentId, Pageable pageable) {
		return repository.queryByReplyToComment_Id(replyToCommentId, pageable);
	}

	@Override
	public Page<Comment> queryBySponsorByUser(Long sponsorByUserId, Pageable pageable) {
		return repository.queryBySponsorByUser_Id(sponsorByUserId, pageable);
	}

	@Override
	public void noticeFriends() {
		throw new NotImplementedException();
	}
}
