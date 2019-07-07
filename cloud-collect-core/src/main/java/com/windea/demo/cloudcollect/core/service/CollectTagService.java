package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏的标签的服务。
 */
public interface CollectTagService {
	/**
	 * 创建自己的标签。
	 */
	void create(CollectTag tag, User user);

	/**
	 * 删除自己的标签。
	 */
	void delete(Long id);

	/**
	 * 修改自己的标签（名字，概述）。
	 */
	void modify(Long id, CollectTag tag);

	/**
	 * 得到某一标签。
	 */
	CollectTag get(Long id);

	/**
	 * 分页得到某一标签的所有收藏。
	 */
	Page<Collect> getCollectPage(Long id, Pageable pageable);

	/**
	 * 得到某一标签的收藏数量。
	 */
	Long getCollectCount(Long id);

	/**
	 * 分页得到所有标签。
	 */
	Page<CollectTag> findAll(Pageable pageable);

	/**
	 * 分页查询某一用户的所有标签。
	 */
	Page<CollectTag> findByUser(Long userId, Pageable pageable);

	/**
	 * 根据名字分页模糊查询某一用户的所有标签。
	 */
	Page<CollectTag> findByUserAndName(Long userId, String name, Pageable pageable);

	/**
	 * 检查某一标签是否已存在。
	 */
	boolean exists(CollectTag tag);
}
