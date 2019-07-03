package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectCategoryService {
	void create(CollectCategory category);

	void delete(Long id);

	void modify(Long id, CollectCategory category);

	CollectCategory get(Long id);

	Page<CollectCategory> queryByUser(Long userId, Pageable pageable);

	Page<CollectCategory> queryByUserAndName(Long userId, String name, Pageable pageable);

	boolean exists(Long userId, String name);
}
