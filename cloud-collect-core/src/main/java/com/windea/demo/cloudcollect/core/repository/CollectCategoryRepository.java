package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectCategoryRepository extends JpaRepository<CollectCategory, Long> {
	Page<CollectCategory> queryByUser(User user, Pageable pageable);

	Page<CollectCategory> queryByUserAndNameContains(User user, String name, Pageable pageable);

	boolean existsByUserAndName(User user, String name);
}
