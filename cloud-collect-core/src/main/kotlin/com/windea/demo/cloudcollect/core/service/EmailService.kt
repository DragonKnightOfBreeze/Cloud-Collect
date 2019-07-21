package com.windea.demo.cloudcollect.core.service

/**邮件的服务。*/
interface EmailService {
	/**TODO 发送邮件。*/
	fun sendEmail()
	
	/**TODO 发送激活邮件。*/
	fun sendActivateEmail()
	
	/**TODO 发送重置密码邮件。*/
	fun sendResetPasswordEmail()
	
	/**TODO 发送欢迎邮件。*/
	fun sendHelloEmail()
}
