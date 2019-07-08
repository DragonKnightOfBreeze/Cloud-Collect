package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * 收藏的服务。
 */
public interface CollectService {
	/**
	 * 创建自己的收藏（去除地址的查询参数）。
	 */
	void create(Collect collect, User user);

	/**
	 * 从别人的收藏创建自己的收藏（默认点赞原始收藏，去除地址的查询参数）。
	 */
	void createFrom(Collect collect, User user);

	/**
	 * 删除自己的收藏（不删除数据库中的数据，而是将deleteStatus设为true）。
	 */
	void delete(Long id);

	/**
	 * 修改自己的收藏（名字，概述，分类，标签，类型）。
	 */
	void modify(Long id, Collect collect);

	/**
	 * 修改自己的收藏的分类。
	 */
	void modifyCategory(Long id, CollectCategory category);

	/**
	 * 修改自己的收藏的标签。
	 */
	void modifyTags(Long id, Set<CollectTag> tags);

	/**
	 * 修改自己的收藏的类型。
	 */
	void modifyType(Long id, CollectType type);

	/**
	 * 点赞某一收藏。
	 */
	void praise(Long id, User user);

	/**
	 * 得到某一收藏。
	 */
	Collect get(Long id);

	/**
	 * 分页得到某一收藏的所有点赞用户。
	 */
	Page<User> getPraiseByUserPage(Long id, Pageable pageable);

	/**
	 * 得到某一收藏的点赞用户数量。
	 */
	Long getPraiseByUserCount(Long id);

	/**
	 * 分页得到某一收藏的所有评论。
	 */
	Page<Comment> getCommentPage(Long id, Pageable pageable);

	/**
	 * 得到某一收藏的评论数量。
	 */
	Long getCommentCount(Long id);

	/**
	 * 分页得到所有收藏。
	 */
	Page<Collect> findAll(Pageable pageable);

	/**
	 * 分页查询某一用户的所有已删除/未删除收藏。
	 */
	Page<Collect> findByUserAndDeleteStatus(Long userId, Boolean deleteStatus, Pageable pageable);

	/**
	 * 根据名字分页模糊查询某一用户的所有收藏。
	 */
	Page<Collect> findByUserAndName(Long userId, String name, Pageable pageable);

	/**
	 * 根据分类分页查询某一用户的所有收藏（分类属于唯一用户）。
	 */
	Page<Collect> findByUserAndCategory(Long categoryId, Pageable pageable);

	/**
	 * 根据标签分页查询某一用户的所有收藏（标签属于唯一用户）。
	 */
	Page<Collect> findByUserAndTag(Long tagId, Pageable pageable);

	/**
	 * 根据类型分页查询某一用户的所有收藏。
	 */
	Page<Collect> findByUserAndType(Long userId, CollectType type, Pageable pageable);

	/**
	 * 根据名字分页全局查询所有收藏。
	 */
	Page<Collect> findByName(String name, Pageable pageable);

	/**
	 * 检查某一收藏是否已存在。
	 */
	boolean exists(Collect collect);
}
