package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectTagRepository extends JpaRepository<CollectTag, Long> {
	Page<CollectTag> queryByUser_Id(Long userId, Pageable pageable);

	Long countByUser_Id(Long userId, Pageable pageable);

	Page<CollectTag> queryByUser_IdAndNameContains(Long userId, String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
