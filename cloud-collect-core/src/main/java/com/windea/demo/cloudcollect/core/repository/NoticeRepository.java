package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
