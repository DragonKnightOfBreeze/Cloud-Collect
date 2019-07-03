package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	Page<Notice> queryByUser_Id(Long userId, Pageable pageable);

	Page<Notice> queryByUser_IdAndType(Long userId, NoticeType type, Pageable pageable);

	Page<Notice> queryByUser_IdAndRead(Long userId, Boolean read, Pageable pageable);

	Page<Notice> queryByUser_IdAndTypeAndRead(Long userId, NoticeType type, Boolean read, Pageable pageable);
}
