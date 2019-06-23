package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectCategoryRepository extends JpaRepository<CollectCategory, Long> {
}
