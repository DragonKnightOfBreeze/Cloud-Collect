package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import com.windea.demo.cloudcollect.core.repository.NoticeRepository;
import com.windea.demo.cloudcollect.core.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
	private final NoticeRepository repository;

	public NoticeServiceImpl(NoticeRepository repository) {
		this.repository = repository;
	}


	@Override
	public void create(Notice notice) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void read(Long id) {

	}

	@Override
	public Notice get(Long id) {
		return null;
	}

	@Override
	public Page<Notice> queryByUserAndType(Long userId, NoticeType type, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Notice> queryByUserAndRead(Long userId, Boolean read, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Notice> queryByUserAndTypeAndRead(Long userId, NoticeType type, Boolean read, Pageable pageable) {
		return null;
	}
}
