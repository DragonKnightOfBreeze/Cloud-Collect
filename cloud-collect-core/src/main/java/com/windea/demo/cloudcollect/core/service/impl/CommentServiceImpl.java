package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.exception.NotFoundException;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CommentRepository;
import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository repository;
	private final CollectRepository collectRepository;

	public CommentServiceImpl(CommentRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}


	@Transactional
	@Override
	public void create(Long collectId, Comment comment, User sponsorByUser) {
		var collect = collectRepository.findById(collectId).orElseThrow(NotFoundException::new);
		comment.setCollect(collect);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);
	}

	@Transactional
	@Override
	public void reply(Long collectId, Long replyToCommentId, Comment comment, User sponsorByUser) {
		var collect = collectRepository.findById(collectId).orElseThrow(NotFoundException::new);
		comment.setCollect(collect);
		var replyToComment = repository.findById(replyToCommentId).orElseThrow(NotFoundException::new);
		comment.setReplyToComment(replyToComment);
		comment.setSponsorByUser(sponsorByUser);
		repository.save(comment);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Cacheable("comment")
	@Override
	public Comment get(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Cacheable("comment.replyByCommentPage")
	@Override
	public Page<Comment> getReplyByCommentPage(Long id, Pageable pageable) {
		return repository.findByReplyToComment_Id(id, pageable);
	}

	@Cacheable("comment.replyByCommentCount")
	@Override
	public Long getReplyByCommentCount(Long id) {
		return repository.countByReplyToComment_Id(id);
	}

	@Cacheable("commentPage")
	@Override
	public Page<Comment> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("commentPage.byComment")
	@Override
	public Page<Comment> findByCollect(Long collectId, Pageable pageable) {
		return repository.findByCollect_Id(collectId, pageable);
	}
}
