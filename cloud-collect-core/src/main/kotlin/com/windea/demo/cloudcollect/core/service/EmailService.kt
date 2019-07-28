package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import javax.mail.internet.*

/**邮件的服务。*/
interface EmailService {
	/**发送邮件。*/
	fun sendEmail(message: MimeMessage, user: User)
	
	/**发送激活邮件。*/
	fun sendActivateEmail(user: User)
	
	/**发送欢迎邮件。*/
	fun sendHelloEmail(user: User)
}
