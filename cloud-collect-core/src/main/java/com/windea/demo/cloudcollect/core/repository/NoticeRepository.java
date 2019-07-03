package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	Page<Notice> queryByUser(User user, Pageable pageable);

	Page<Notice> queryByUserAndType(User user, NoticeType type, Pageable pageable);

	Page<Notice> queryByUserAndRead(User user, Boolean read, Pageable pageable);

	Page<Notice> queryByUserAndTypeAndRead(User user, NoticeType type, Boolean read, Pageable pageable);
}
