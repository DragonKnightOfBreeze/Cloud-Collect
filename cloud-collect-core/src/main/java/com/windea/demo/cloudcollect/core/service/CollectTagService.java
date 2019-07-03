package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectTagService {
	void create(CollectTag tag);

	void delete(Long id);

	void modify(Long id, CollectTag tag);

	CollectTag get(Long id);

	Page<CollectTag> queryByUser(Long userId, Pageable pageable);

	Page<CollectTag> queryByUserAndName(Long userId, String name, Pageable pageable);

	boolean exists(Long userId, String name);
}
