package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectTagRepository extends JpaRepository<CollectTag, Long> {
	Optional<CollectTag> findByName(String name);

	Page<CollectTag> findByUser_Id(Long userId, Pageable pageable);

	long countByUser_Id(Long userId);

	Page<CollectTag> findByUser_IdAndNameContains(Long userId, String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
