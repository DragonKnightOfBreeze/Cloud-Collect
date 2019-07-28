package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.annotation.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.boot.context.properties.*
import org.springframework.mail.javamail.*
import org.springframework.stereotype.*
import javax.mail.internet.*

@Service
@ConfigurationProperties("spring.mail")
@NotTested("未进行实际测试……")
open class EmailServiceImpl(
	private val mailSender: JavaMailSender
) : EmailService {
	lateinit var username: String
	
	
	override fun sendEmail(message: MimeMessage, user: User) {
		val helper = MimeMessageHelper(message)
		helper.setFrom(username)
		helper.setTo(user.email)
		mailSender.send(message)
	}
	
	override fun sendActivateEmail(user: User) {
		val activateUrl = "http://csntportal/api/activate?username=${user.username}&activateCode=${user.activateCode}"
		
		val subject = "云收藏：激活你的用户"
		//language=HTML
		val text = """
		<html lang="zh">
		  <h1>云收藏：激活你的用户</h1>
		  <p>
		    请点击<a href="$activateUrl">此地址</a>激活您的账户。
		  </p>
		</html>
		""".trimIndent()
		
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message)
		helper.setFrom(username)
		helper.setTo(user.email)
		helper.setSubject(subject)
		helper.setText(text)
		mailSender.send(message)
	}
	
	override fun sendHelloEmail(user: User) {
		val subject = "云收藏：欢迎使用"
		//language=HTML
		val text = """
		<html lang="zh">
		  <h1>云收藏：欢迎使用</h1>
		  <p>
		    欢迎使用微风的龙骑士的云收藏项目！
		  </p>
		</html>
		""".trimIndent()
		
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message)
		helper.setFrom(username)
		helper.setTo(user.email)
		helper.setSubject(subject)
		helper.setText(text)
		mailSender.send(message)
	}
}
