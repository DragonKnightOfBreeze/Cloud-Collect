package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.exception.NotImplementedException;
import com.windea.demo.cloudcollect.core.repository.CommentRepository;
import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository repository;

	public CommentServiceImpl(CommentRepository repository) {
		this.repository = repository;
	}


	@Transactional
	@Override
	public void create(Comment comment, Collect collect, User sponsorByUser) {
		comment.setCollect(collect);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);

		noticeFriends();
	}

	@Transactional
	@Override
	public void replyTo(Comment comment, Collect collect, Comment replyToComment, User sponsorByUser) {
		comment.setCollect(collect);
		comment.setReplyToComment(replyToComment);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);

		noticeFriends();
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Comment get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Comment> getReplyByCommentPage(Long id, Pageable pageable) {
		return repository.queryByReplyToComment_Id(id, pageable);
	}

	@Override
	public Long getReplyByCommentCount(Long id) {
		return repository.countByReplyToComment_Id(id);
	}

	@Override
	public Page<Comment> queryByCollect(Long collectId, Pageable pageable) {
		return repository.queryByCollect_Id(collectId, pageable);
	}

	@Override
	public void noticeFriends() {
		throw new NotImplementedException();
	}
}
