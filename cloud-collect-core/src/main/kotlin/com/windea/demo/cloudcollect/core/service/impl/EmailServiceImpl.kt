package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.annotations.marks.*
import org.springframework.beans.factory.annotation.*
import org.springframework.mail.javamail.*
import org.springframework.stereotype.*

@Service
@NotTested("未进行实际测试……")
open class EmailServiceImpl(
	private val mailSender: JavaMailSender
) : EmailService {
	@Value("\${spring.mail.username}")
	private lateinit var username: String
	
	
	override fun sendActivateEmail(user: User) {
		val url = "http://csntportal/activate?username=${user.username}&activateCode=${user.activateCode}"
		
		val subject = "云收藏：激活你的用户"
		//language=HTML
		val text = """
		<html lang="zh">
		  <h1>云收藏：激活你的用户</h1>
		  <p>
		    请点击<a href="$url">此地址</a>激活您的账户。
		  </p>
		</html>
		""".trimIndent()
		
		sendEmail(user) {
			setSubject(subject)
			setText(text)
		}
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
		
		sendEmail(user) {
			setSubject(subject)
			setText(text)
		}
	}
	
	override fun sendResetPasswordEmail(user: User) {
		val url = "http://csntportal/resetPassword?username=${user.username}&resetPasswordCode=${user.resetPasswordCode}"
		
		val subject = "云收藏：重置你的密码"
		//language=HTML
		val text = """
		<html lang="zh">
		  <h1>云收藏：重置你的密码</h1>
		  <p>
		    请点击<a href="$url">此地址</a>重置你的密码。
		  </p>
		</html>
		""".trimIndent()
		
		sendEmail(user) {
			setSubject(subject)
			setText(text)
		}
	}
	
	override fun sendResetPasswordSuccessEmail(user: User) {
		val subject = "云收藏：重置密码成功"
		//language=HTML
		val text = """
		<html lang="zh">
		  <h1>云收藏：欢迎使用</h1>
		  <p>
		    已成功重置你的密码。
		  </p>
		</html>
		""".trimIndent()
		
		sendEmail(user) {
			setSubject(subject)
			setText(text)
		}
	}
	
	private fun sendEmail(user: User, handler: MimeMessageHelper.() -> Unit) {
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message)
		helper.setFrom(username)
		helper.setTo(user.email)
		handler(helper)
		mailSender.send(message)
	}
}
