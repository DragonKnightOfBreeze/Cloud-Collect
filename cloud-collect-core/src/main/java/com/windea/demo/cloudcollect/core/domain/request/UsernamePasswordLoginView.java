package com.windea.demo.cloudcollect.core.domain.request;

import com.windea.demo.cloudcollect.core.validation.annotation.Password;
import com.windea.demo.cloudcollect.core.validation.annotation.Username;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户名&密码登录视图。
 */
@Data
public class UsernamePasswordLoginView implements LoginView {
	private static final long serialVersionUID = 8037232370825960415L;

	@NotEmpty(message = "validation.User.username.NotEmpty")
	@Username(message = "validation.User.username.ValidUsername")
	private final String username;

	@NotEmpty(message = "validation.User.password.NotEmpty")
	@Password(message = "validation.User.password.ValidPassword")
	private final String password;
}
