package com.windea.demo.cloudcollect.core.domain.response;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO 收藏家视图。
 */
@Data
@NoArgsConstructor
public class CollectorView implements Serializable {
	private static final long serialVersionUID = 9035220751877722972L;

	private User mostCollectUser;

	private User mostFollowedUser;

	private User mostPraisedUser;

	private User mostCommentedUser;

	private User mostPopularUser;

	private User mostActiveUser;
}
