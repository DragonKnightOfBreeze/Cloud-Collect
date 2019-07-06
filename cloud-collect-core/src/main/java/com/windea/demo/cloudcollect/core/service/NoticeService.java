package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 通知的服务类。
 */
public interface NoticeService {
	/**
	 * 创建通知。
	 */
	void create(Notice notice, User user);

	/**
	 * 删除通知。
	 */
	void delete(Long id);

	/**
	 * 阅读通知（将read设为true）。
	 */
	void read(Long id);

	/**
	 * 得到某一通知。
	 */
	Notice get(Long id);

	/**
	 * 查询某一用户的所有通知。
	 */
	Page<Notice> queryByUser(Long userId, Pageable pageable);

	/**
	 * 分页查询某一用户的所有已读/未读通知。
	 */
	Page<Notice> queryByUserAndRead(Long userId, Boolean read, Pageable pageable);
}
