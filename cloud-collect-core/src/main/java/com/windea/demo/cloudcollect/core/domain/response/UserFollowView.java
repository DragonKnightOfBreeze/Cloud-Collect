package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.User;

import java.util.List;

/**
 * 用户关注信息视图。
 */
public interface UserFollowView {
	Long getId();

	String getNickname();

	List<User> getFollowToUserList();

	List<User> getFollowByUserList();
}
