package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*

interface EmailService {
	/**发送激活邮件。*/
	fun sendActivateEmail(activateCode: String, user: User)
	
	/**发送欢迎邮件。*/
	fun sendHelloEmail(user: User)
	
	/**发送重置密码邮件。*/
	fun sendResetPasswordEmail(resetPasswordCode: String,
		user: User)
	
	/**发送重置密码成功邮件。*/
	fun sendResetPasswordSuccessEmail(user: User)
}
