package com.windea.demo.cloudcollect.domain.view;

/**
 * 用户名&密码登录视图。
 */
public class UsernamePasswordLoginView implements UserLoginView {
	private static final long serialVersionUID = 8037232370825960415L;

	private String username;

	private String password;


	public UsernamePasswordLoginView(String username, String password) {
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
