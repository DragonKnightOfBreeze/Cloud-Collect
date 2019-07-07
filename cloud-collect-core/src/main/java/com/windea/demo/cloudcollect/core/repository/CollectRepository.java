package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectRepository extends JpaRepository<Collect, Long> {
	Page<Collect> findByUser_IdAndDeleted(Long userId, Boolean deleted, Pageable pageable);

	Long countByUser_IdAndDeleted(Long userId, Boolean deleted);

	Page<Collect> findByUser_IdAndNameContainsAndDeletedFalse(Long userId, String name, Pageable pageable);

	Page<Collect> findByCategory_IdAndDeletedFalse(Long categoryId, Pageable pageable);

	Long countByCategory_IdAndDeletedFalse(Long categoryId);

	@Query("from Collect c, in(c.tags) t where t.id=:tagId and c.deleted=false")
	Page<Collect> findByTag_IdAndDeletedFalse(Long tagId, Pageable pageable);

	@Query("select count(c) from Collect c, in(c.tags) t where t.id=:tagId and c.deleted=false")
	Long countByTag_IdAndDeletedFalse(Long tagId);

	Page<Collect> findByUser_IdAndTypeAndDeletedFalse(Long userId, CollectType type, Pageable pageable);

	Long countByUser_IdAndTypeAndDeletedFalse(Long userId, CollectType type);

	@Query("from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.deleted=false")
	Page<Collect> findByPraiseByUser_IdAndDeletedFalse(Long praiseByUserId, Pageable pageable);

	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.deleted=false")
	Long countByPraiseByUser_IdAndDeletedFalse(Long praiseByUserId);

	Page<Collect> findByNameContainsAndDeletedFalse(String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
