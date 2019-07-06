package com.windea.demo.cloudcollect.core.domain.view;

import com.windea.demo.cloudcollect.core.validation.annotation.Password;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 邮箱&密码登录视图。
 */
@Data
public class EmailPasswordLoginView implements LoginView {
	private static final long serialVersionUID = 227387088818335601L;

	@NotEmpty(message = "validation.User.email.NotEmpty")
	@Email(message = "validation.User.email.Email")
	private final String email;

	@NotEmpty(message = "validation.User.password.NotEmpty")
	@Password(message = "validation.User.password.ValidPassword")
	private final String password;
}
