package com.windea.demo.cloudcollect.core.domain.view;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO 收藏家视图。
 */
@Data
public class CollectorView implements Serializable {
	private static final long serialVersionUID = 9035220751877722972L;

	private User mostCollectUser;

	private User mostFollowedUser;

	private User mostPraisedUser;

	private User mostCommentedUser;

	private User mostPopularUser;

	private User mostActiveUser;
}
