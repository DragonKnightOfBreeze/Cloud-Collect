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
	 * 创建某一用户的通知。
	 */
	Notice create(Notice notice, User user);

	/**
	 * 创建通知，发送给所有用户。
	 */
	void sendToAll(Notice notice);

	/**
	 * 删除通知。
	 */
	void delete(Long id);

	/**
	 * 阅读自己的通知。将readStatus设为true。
	 */
	Notice read(Long id);

	/**
	 * 得到某一通知。
	 */
	Notice get(Long id);

	/**
	 * 分页得到所有通知。
	 */
	Page<Notice> findAll(Pageable pageable);

	/**
	 * 分页查询某一用户的所有通知。
	 */
	Page<Notice> findByUser(Long userId, Pageable pageable);

	/**
	 * 分页查询某一用户的所有已读/未读通知。
	 */
	Page<Notice> findByUserAndReadStatus(Long userId, Boolean readStatus, Pageable pageable);
}
