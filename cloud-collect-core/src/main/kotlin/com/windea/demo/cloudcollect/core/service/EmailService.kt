package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*

interface EmailService {
	/**发送激活邮件。*/
	fun sendActivateEmail(user: User, activateCode: String)
	
	/**发送欢迎邮件。*/
	fun sendHelloEmail(user: User)
	
	/**发送重置密码邮件。*/
	fun sendResetPasswordEmail(user: User, resetPasswordCode: String)
	
	/**发送重置密码成功邮件。*/
	fun sendResetPasswordSuccessEmail(user: User)
}
