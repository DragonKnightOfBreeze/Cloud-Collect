package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
	void create(Notice notice);

	void delete(Long id);

	void read(Long id);

	Notice get(Long id);

	Page<Notice> queryByUserAndType(Long userId, NoticeType type,
		org.springframework.data.domain.Pageable pageable);

	Page<Notice> queryByUserAndRead(Long userId, Boolean read, org.springframework.data.domain.Pageable pageable);

	Page<Notice> queryByUserAndTypeAndRead(Long userId, NoticeType type, Boolean read, Pageable pageable);
}
