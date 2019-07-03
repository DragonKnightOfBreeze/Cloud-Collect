package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectPrivacy;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.domain.response.CollectPraiseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Set;

public interface CollectRepository extends JpaRepositoryImplementation<Collect, Long> {
	Page<Collect> queryByUserAndDeletedFalse(User user, Pageable pageable);

	Page<Collect> queryByUserAndNameContainsAndDeletedFalse(User user, String name, Pageable pageable);

	Page<Collect> queryByCategoryAndDeletedFalse(CollectCategory category, Pageable pageable);

	Page<Collect> queryByTagsInAndDeletedFalse(Set<CollectTag> tags, Pageable pageable);

	Page<Collect> queryByUserAndTypeAndDeletedFalse(User user, CollectType type, Pageable pageable);

	Page<Collect> queryByUserAndPrivacyAndDeletedFalse(User user, CollectPrivacy privacy, Pageable pageable);

	Page<Collect> queryByCategoryAndPrivacyAndDeletedFalse(CollectCategory category, CollectPrivacy privacy,
		Pageable pageable);

	Page<Collect> queryByTagsAndPrivacyAndDeletedFalse(Set<CollectTag> tags, CollectPrivacy privacy,
		Pageable pageable);

	Page<Collect> queryByUserAndDeletedTrue(User user, Pageable pageable);

	Page<Collect> queryByNameContains(String name, Pageable pageable);

	boolean existsByUserAndName(User user, String name);

	@Query("select c.id, c.name, c.praiseByUserList from Collect c where c.id = :id")
	CollectPraiseView getPraiseView(Long id);
}
