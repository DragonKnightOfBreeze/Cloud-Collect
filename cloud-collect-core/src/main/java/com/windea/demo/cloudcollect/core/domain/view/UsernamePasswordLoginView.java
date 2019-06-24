package com.windea.demo.cloudcollect.core.domain.view;

import lombok.Data;

/**
 * 用户名&密码登录视图。
 */
@Data
public class UsernamePasswordLoginView implements UserLoginView {
	private static final long serialVersionUID = 8037232370825960415L;

	private final String username;

	private final String password;
}
