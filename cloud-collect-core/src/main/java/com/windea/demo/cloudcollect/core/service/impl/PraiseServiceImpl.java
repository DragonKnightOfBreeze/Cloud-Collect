package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.repository.PraiseRepository;
import com.windea.demo.cloudcollect.core.service.PraiseService;
import org.springframework.stereotype.Service;

@Service
public class PraiseServiceImpl implements PraiseService {
	private final PraiseRepository repository;

	public PraiseServiceImpl(PraiseRepository repository) {
		this.repository = repository;
	}
}
