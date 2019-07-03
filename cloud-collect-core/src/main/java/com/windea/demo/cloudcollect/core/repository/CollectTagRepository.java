package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectTagRepository extends JpaRepository<CollectTag, Long> {
	Page<CollectTag> queryByUser(User user, Pageable pageable);

	Page<CollectTag> queryByUserAndNameContains(User user, String name, Pageable pageable);

	boolean existsByUserAndName(User user, String name);
}
