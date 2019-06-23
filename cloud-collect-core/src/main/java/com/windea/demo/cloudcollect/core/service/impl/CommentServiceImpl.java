package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.repository.CommentRepository;
import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository repository;
	private final CollectRepository collectRepository;

	public CommentServiceImpl(CommentRepository repository, CollectRepository collectRepository) {
		this.repository = repository;
		this.collectRepository = collectRepository;
	}
}
