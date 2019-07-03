package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CollectService {
	void create(Collect collect, Long userId);

	void createFrom(Collect collect, Long userId, Long fromUserId);

	void delete(Long id);

	void modify(Long id, Collect collect);

	void modifyCategory(Long id, CollectCategory category);

	void modifyTags(Long id, Set<CollectTag> tags);

	void modifyType(Long id, CollectType type);

	void praise(Long id, Long userId);

	Collect get(Long id);

	Page<Collect> queryByUser(Long userId, Pageable pageable);

	Page<Collect> queryByUserDeleted(Long userId, Pageable pageable);

	Page<Collect> queryByUserAndName(Long userId, String name, Pageable pageable);

	Page<Collect> queryByUserAndCategory(Long userId, Long categoryId, Pageable pageable);

	Page<Collect> queryByUserAndTag(Long userId, Long categoryId, Pageable pageable);

	Page<Collect> queryByUserAndType(Long userId, CollectType type, Pageable pageable);

	Page<Collect> queryByName(String name, Pageable pageable);

	Page<Collect> queryByPraiseByUser(Long praiseByUserId, Pageable pageable);

	boolean exists(Long userId, String name);

	void noticeFollowByUser();
}
