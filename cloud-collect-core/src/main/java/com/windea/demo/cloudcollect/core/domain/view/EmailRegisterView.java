package com.windea.demo.cloudcollect.core.domain.view;

import com.windea.demo.cloudcollect.core.validation.annotation.Password;
import com.windea.demo.cloudcollect.core.validation.annotation.Username;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 邮箱注册视图。
 */
@Data
public class EmailRegisterView implements RegisterView {
	private static final long serialVersionUID = 1589816809638668733L;

	@NotEmpty(message = "validation.User.nickname.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.User.nickname.Size")
	private final String nickname;

	@NotEmpty(message = "validation.User.username.NotEmpty")
	@Username(message = "validation.User.username.ValidUsername")
	private final String username;

	@NotEmpty(message = "validation.User.email.NotEmpty")
	@Email(message = "validation.User.email.Email")
	private final String email;

	@NotEmpty(message = "validation.User.password.NotEmpty")
	@Password(message = "validation.User.password.ValidPassword")
	private final String password;
}
