package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.repository.NoticeRepository;
import com.windea.demo.cloudcollect.core.service.NoticeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {
	private final NoticeRepository repository;

	public NoticeServiceImpl(NoticeRepository repository) {
		this.repository = repository;
	}


	@Transactional
	@Override
	public void create(Notice notice, User user) {
		notice.setUser(user);
		repository.save(notice);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public void read(Long id) {
		var notice = repository.getOne(id);
		notice.setRead(true);
		repository.save(notice);
	}

	@Cacheable("notice")
	@Override
	public Notice get(Long id) {
		return repository.getOne(id);
	}

	@Cacheable("noticePage")
	@Override
	public Page<Notice> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable("noticePage.byUser")
	@Override
	public Page<Notice> findByUser(Long userId, Pageable pageable) {
		return repository.findByUser_Id(userId, pageable);
	}

	@Cacheable("noticePage.byUserAndRead")
	@Override
	public Page<Notice> findByUserAndRead(Long userId, Boolean read, Pageable pageable) {
		return repository.findByUser_IdAndRead(userId, read, pageable);
	}
}
