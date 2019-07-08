package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	Page<Notice> findByUser_Id(Long userId, Pageable pageable);

	long countByUser_Id(Long userId);

	Page<Notice> findByUser_IdAndReadStatus(Long userId, Boolean readStatus, Pageable pageable);

	long countByUser_IdAndReadStatus(Long userId, Boolean readStatus);
}
