package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.domain.view.EmailRegisterView;
import com.windea.demo.cloudcollect.core.domain.view.UsernamePasswordLoginView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户的服务（登录、重置密码等功能委托给{@code UserDetailsService}）。
 */
public interface UserService {
	/**
	 * 通过用户名&密码登录用户。
	 */
	User loginByUsernameAndPassword(UsernamePasswordLoginView view);

	/**
	 * 通过邮箱注册用户（密码需要加密）。
	 */
	void registerByEmail(EmailRegisterView view);

	/**
	 * 激活用户（将activated设为true）。
	 */
	void activate(User user);

	/**
	 * 重置用户密码（密码需要加密）。
	 */
	void resetPassword(User user, String newPassword);

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
	 * 得到某一用户的关注用户数量。
	 */
	Long getFollowToUserCount(Long id);

	/**
	 * 分页得到某一用户的所有粉丝用户。
	 */
	Page<User> getFollowByUserPage(Long id, Pageable pageable);

	/**
	 * 得到某一用户的粉丝用户数量。
	 */
	Long getFollowByUserCount(Long id);

	/**
	 * 分页得到某一用户的所有收藏。
	 */
	Page<Collect> getCollectPage(Long id, Pageable pageable);

	/**
	 * 得到某一用户的收藏数量。
	 */
	Long getCollectCount(Long id);

	/**
	 * 得到某一用户的所有收藏分类。
	 */
	Page<CollectCategory> getCollectCategoryPage(Long id, Pageable pageable);

	/**
	 * 得到某一用户的所有收藏分类数量。
	 */
	Long getCollectCategoryCount(Long id);

	/**
	 * 分页得到某一用户的所有通知。
	 */
	Page<Notice> getNoticePage(Long id, Pageable pageable);

	/**
	 * 得到某一用户的通知数量。
	 */
	Long getNoticeCount(Long id);

	/**
	 * 分页得到所有用户。
	 */
	Page<User> findAll(Pageable pageable);

	/**
	 * 根据昵称分页全局模糊查询用户。
	 */
	Page<User> findByNickname(String nickname, Pageable pageable);

	/**
	 * 根据身份分页全局查询用户。
	 */
	Page<User> findByRole(Role role, Pageable pageable);

	/**
	 * 检查某一用户是否已存在。
	 */
	boolean exists(User user);
}
