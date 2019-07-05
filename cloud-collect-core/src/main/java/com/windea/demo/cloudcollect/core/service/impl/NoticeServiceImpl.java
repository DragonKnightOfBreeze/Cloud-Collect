package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
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
		repository.save(notice);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void read(Long id) {
		var notice = repository.getOne(id);
		notice.setRead(true);
		repository.save(notice);
	}

	@Override
	public Notice get(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Page<Notice> queryByUser(Long userId, Pageable pageable) {
		return repository.queryByUser_Id(userId, pageable);
	}

	@Override
	public Page<Notice> queryByUserAndRead(Long userId, Boolean read, Pageable pageable) {
		return repository.queryByUser_IdAndRead(userId, read, pageable);
	}
}
