package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.User;

import java.util.List;

/**
 * 收藏点赞信息视图。
 */
public interface CollectPraiseView {
	Long getId();

	String getName();

	List<User> getPraiseByUserList();
}
