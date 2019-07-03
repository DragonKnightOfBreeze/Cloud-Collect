package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.User;

//TODO

/**
 * 收藏家视图。
 */
public interface CollectorView {
	User getMostCollectUser();

	User getMostFollowedUser();

	User getMostPraisedUser();

	User getMostCommentedUser();

	User getMostPopularUser();

	User getMostActiveUser();
}
