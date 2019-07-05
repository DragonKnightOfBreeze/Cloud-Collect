package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectRepository extends JpaRepository<Collect, Long> {
	Page<Collect> queryByUser_IdAndDeletedFalse(Long userId, Pageable pageable);

	Page<Collect> queryByUser_IdAndDeletedTrue(Long userId, Pageable pageable);

	Page<Collect> queryByUser_IdAndNameContainsAndDeletedFalse(Long userId, String name, Pageable pageable);

	Page<Collect> queryByCategory_IdAndDeletedFalse(Long categoryId, Pageable pageable);

	@Query("from Collect c, in(c.tags) t where t.id = :tagId and c.deleted = false")
	Page<Collect> queryByTag_IdAndDeletedFalse(Long tagId, Pageable pageable);

	Page<Collect> queryByUser_IdAndTypeAndDeletedFalse(Long userId, CollectType type, Pageable pageable);

	Page<Collect> queryByNameContainsAndDeletedFalse(String name, Pageable pageable);

	@Query("from Collect c, in(c.praiseByUserList) u where u.id = :praiseByUserId and c.deleted = false")
	Page<Collect> queryByPraiseByUser_IdAndDeletedFalse(Long praiseByUserId, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
