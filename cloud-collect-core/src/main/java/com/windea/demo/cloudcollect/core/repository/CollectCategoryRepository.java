package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectCategoryRepository extends JpaRepository<CollectCategory, Long> {
	Optional<CollectCategory> findByName(String name);

	Page<CollectCategory> findByUser_Id(Long userId, Pageable pageable);

	long countByUser_Id(Long userId);

	Page<CollectCategory> findByUser_IdAndNameContains(Long userId, String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
