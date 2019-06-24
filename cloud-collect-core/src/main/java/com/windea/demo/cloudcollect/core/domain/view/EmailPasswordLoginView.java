package com.windea.demo.cloudcollect.core.domain.view;

import lombok.Data;

/**
 * 用户邮箱&密码登录视图。
 */
@Data
public class EmailPasswordLoginView implements UserLoginView {
	private static final long serialVersionUID = 227387088818335601L;

	private final String email;

	private final String password;
}
