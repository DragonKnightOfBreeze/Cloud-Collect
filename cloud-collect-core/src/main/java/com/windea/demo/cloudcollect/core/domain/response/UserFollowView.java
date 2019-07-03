package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.User;

import java.util.List;

/**
 * 用户关注信息视图。
 */
public interface UserFollowView {
	Long getId();

	String getName();

	List<User> getFollowToUserList();

	List<User> getFollowByUserList();

	default Integer getFollowToUserCount() {
		return getFollowToUserList().size();
	}

	default Integer getFollowByUserCount() {
		return getFollowByUserList().size();
	}
}
