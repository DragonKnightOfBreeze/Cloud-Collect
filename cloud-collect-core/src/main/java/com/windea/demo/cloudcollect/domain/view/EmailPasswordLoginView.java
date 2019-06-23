package com.windea.demo.cloudcollect.domain.view;

/**
 * 用户邮箱&密码登录视图。
 */
public class EmailPasswordLoginView implements UserLoginView {
	private static final long serialVersionUID = 227387088818335601L;

	private String email;

	private String password;


	public EmailPasswordLoginView(String email, String password) {
		this.email = email;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
