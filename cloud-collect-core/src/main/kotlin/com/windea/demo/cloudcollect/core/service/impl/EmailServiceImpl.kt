package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.boot.autoconfigure.mail.*
import org.springframework.mail.javamail.*
import org.springframework.stereotype.*

@Service
class EmailServiceImpl(
	private val mailSender: JavaMailSender,
	private val mailProperties: MailProperties
) : EmailService {
	override fun sendActivateEmail(user: User, activateCode: String) = mailSender.sendEmail {
		val url = "http://activate?username=${user.username}&activateCode=$activateCode"
		
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：激活你的用户")
		//language=HTML
		setText("""
			<html lang="zh">
			  <h1>云收藏：激活你的用户</h1>
			  <p>
			    请点击<a href="$url">此地址</a>激活您的账户。
			  </p>
			</html>
		""".trimIndent())
	}
	
	override fun sendHelloEmail(user: User) = mailSender.sendEmail {
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：欢迎使用")
		//language=HTML
		setText("""
			<html lang="zh">
			  <h1>云收藏：欢迎使用</h1>
			  <p>
			    欢迎使用微风的龙骑士的云收藏项目！
			  </p>
			</html>
		""".trimIndent())
	}
	
	override fun sendResetPasswordEmail(user: User, resetPasswordCode: String) = mailSender.sendEmail {
		val url = "http://resetPassword?username=${user.username}&resetPasswordCode=$resetPasswordCode"
		
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：重置你的密码")
		//language=HTML
		setText("""
			<html lang="zh">
			  <h1>云收藏：重置你的密码</h1>
			  <p>
			    请点击<a href="$url">此地址</a>重置你的密码。
			  </p>
			</html>
		""".trimIndent())
	}
	
	override fun sendResetPasswordSuccessEmail(user: User) = mailSender.sendEmail {
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：重置密码成功")
		//language=HTML
		setText("""
			<html lang="zh">
			  <h1>云收藏：欢迎使用</h1>
			  <p>
			    已成功重置你的密码。
			  </p>
			</html>
		""".trimIndent())
	}
	
	
	private fun JavaMailSender.sendEmail(encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) {
		try {
			this.send {
				val helper = MimeMessageHelper(it, encoding)
				helper.prepare()
			}
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}
