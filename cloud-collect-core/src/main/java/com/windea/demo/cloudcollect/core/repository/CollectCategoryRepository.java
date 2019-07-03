package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectCategoryRepository extends JpaRepository<CollectCategory, Long> {
	Page<CollectCategory> queryByUser_Id(Long userId, Pageable pageable);

	Page<CollectCategory> queryByUser_IdAndNameContains(Long userId, String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
