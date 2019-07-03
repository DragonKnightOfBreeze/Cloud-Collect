package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;

import java.util.List;

/**
 * 用户点赞信息视图。
 */
public interface UserPraiseView {
	Long getId();

	String getNickname();

	List<Collect> getPraiseToCollectList();
}
