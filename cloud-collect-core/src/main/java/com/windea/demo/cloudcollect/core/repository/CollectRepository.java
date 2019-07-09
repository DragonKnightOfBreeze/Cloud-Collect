package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CollectRepository extends JpaRepository<Collect, Long> {
	Optional<Collect> findByName(String name);

	Page<Collect> findByUser_IdAndDeleteStatus(Long userId, Boolean deleteStatus, Pageable pageable);

	long countByUser_IdAndDeleteStatus(Long userId, Boolean deleteStatus);

	Page<Collect> findByUser_IdAndNameContainsAndDeleteStatusFalse(Long userId, String name, Pageable pageable);

	Page<Collect> findByCategory_IdAndDeleteStatusFalse(Long categoryId, Pageable pageable);

	long countByCategory_IdAndDeleteStatusFalse(Long categoryId);

	@Query("from Collect c, in(c.tags) t where t.id=:tagId and c.deleteStatus=false")
	Page<Collect> findByTag_IdAndDeleteStatusFalse(Long tagId, Pageable pageable);

	@Query("select count(c) from Collect c, in(c.tags) t where t.id=:tagId and c.deleteStatus=false")
	long countByTag_IdAndDeleteStatusFalse(Long tagId);

	Page<Collect> findByUser_IdAndTypeAndDeleteStatusFalse(Long userId, CollectType type, Pageable pageable);

	long countByUser_IdAndTypeAndDeleteStatusFalse(Long userId, CollectType type);

	@Query("from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c.deleteStatus=false")
	Page<Collect> findByPraiseByUser_IdAndDeleteStatusFalse(Long praiseByUserId, Pageable pageable);

	@Query("select count(c) from Collect c, in(c.praiseByUserList) u where u.id=:praiseByUserId and c" +
		".deleteStatus=false")
	long countByPraiseByUser_IdAndDeleteStatusFalse(Long praiseByUserId);

	Page<Collect> findByNameContainsAndDeleteStatusFalse(String name, Pageable pageable);

	boolean existsByUser_IdAndName(Long userId, String name);
}
