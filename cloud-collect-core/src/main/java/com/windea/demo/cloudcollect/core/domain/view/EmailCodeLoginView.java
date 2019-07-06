package com.windea.demo.cloudcollect.core.domain.view;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 邮箱&验证码登录视图。
 */
@Data
public class EmailCodeLoginView implements LoginView {
	private static final long serialVersionUID = 536724125403306865L;

	@NotEmpty(message = "validation.User.email.NotEmpty")
	@Email(message = "validation.User.email.Email")
	private final String email;

	@NotEmpty(message = "validation.User.code.NotEmpty")
	@Size(min = 6, max = 6, message = "validation.User.code.Size")
	private final String code;
}
