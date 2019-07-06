package com.windea.demo.cloudcollect.core.service;

/**
 * 邮件的服务。
 */
public interface EmailService {
	/**
	 * TODO 发送邮件。
	 */
	void sendEmail();

	/**
	 * TODO 发送激活邮件。
	 */
	void sendActivateEmail();

	/**
	 * TODO 发送重置密码邮件。
	 */
	void sendResetPasswordEmail();

	/**
	 * TODO 发送欢迎邮件。
	 */
	void sendHelloEmail();
}
