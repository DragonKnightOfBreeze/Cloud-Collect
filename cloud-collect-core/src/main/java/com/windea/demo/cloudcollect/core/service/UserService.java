package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户的服务（登录、重置密码等功能委托给{@code UserDetailsService}）。
 */
public interface UserService {
	/**
	 * 注册用户（密码需要在控制层加密）。
	 */
	void register(User user);

	/**
	 * 激活用户（将activated设为true）。
	 */
	void activate(Long userId);

	/**
	 * 更新用户信息（不允许同时修改密码）。
	 */
	void update(Long id, User user);

	/**
	 * 得到用户信息。
	 */
	User get(Long id);

	/**
	 * 分页得到某一用户的所有关注用户。
	 */
	Page<User> getFollowToUserPage(Long id, Pageable pageable);

	/**
	 * 分页得到某一用户的所有粉丝用户。
	 */
	Page<User> getFollowByUserPage(Long id, Pageable pageable);

	/**
	 * 分页得到某一用户的所有收藏。
	 */
	Page<Collect> getCollectPage(Long id, Pageable pageable);

	/**
	 * 分页得到某一用户的所有评论。
	 */
	Page<Comment> getCommentPage(Long id, Pageable pageable);

	/**
	 * 分页得到某一用户的所有通知。
	 */
	Page<Notice> getNoticePage(Long id, Pageable pageable);

	/**
	 * 根据昵称分页全局模糊查询用户。
	 */
	Page<User> queryByNickname(String nickname, Pageable pageable);

	/**
	 * 根据身份分页全局查询用户。
	 */
	Page<User> queryByRole(Role role, Pageable pageable);

	/**
	 * 根据关注用户分页全局查询用户。
	 */
	Page<User> queryByFollowToUser(Long followToUserId, Pageable pageable);

	/**
	 * 根据粉丝用户分类全局查询用户。
	 */
	Page<User> queryByFollowByUser(Long followByUserId, Pageable pageable);

	/**
	 * 根据点赞用户分类全局查询用户。
	 */
	Page<User> queryByPraiseToCollect(Long praiseToCollectId, Pageable pageable);

	/**
	 * 检查某一用户是否已存在。
	 */
	boolean exists(User user);
}
