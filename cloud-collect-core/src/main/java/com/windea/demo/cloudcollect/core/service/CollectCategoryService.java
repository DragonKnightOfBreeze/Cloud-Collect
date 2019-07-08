package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏的分类的服务。
 */
public interface CollectCategoryService {
	/**
	 * 创建自己的分类。
	 */
	void create(CollectCategory category, User user);

	/**
	 * 删除自己的分类。
	 */
	void delete(Long id);

	/**
	 * 修改自己的分类。名字，概述。
	 */
	void modify(Long id, CollectCategory category);

	/**
	 * 得到某一分类。
	 */
	CollectCategory get(Long id);

	/**
	 * 分页得到某一分类的所有收藏。
	 */
	Page<Collect> getCollectPage(Long id, Pageable pageable);

	/**
	 * 得到某一分类的收藏数量。
	 */
	Long getCollectCount(Long id);

	/**
	 * 分页得到所有分类。
	 */
	Page<CollectCategory> findAll(Pageable pageable);

	/**
	 * 分页得到某一用户的所有分类。
	 */
	Page<CollectCategory> findByUser(Long userId, Pageable pageable);

	/**
	 * 根据名字分页模糊查询某一用户的所有分类。
	 */
	Page<CollectCategory> findByUserAndName(Long userId, String name, Pageable pageable);

	/**
	 * 检查某一分类是否已存在。
	 */
	boolean exists(CollectCategory category);
}
