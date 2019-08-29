package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.annotations.marks.*
import org.springframework.boot.autoconfigure.mail.*
import org.springframework.mail.javamail.*
import org.springframework.stereotype.*

@Service
@NotTested("未进行实际测试……")
open class EmailServiceImpl(
	private val mailSender: JavaMailSender,
	private val mailProperties: MailProperties
) : EmailService {
	override fun sendActivateEmail(user: User) = mailSender.sendEmail {
		val url = "http://csntportal/activate?username=${user.username}&activateCode=${user.activateCode}"
		
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：激活你的用户")
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
		setText("""
		<html lang="zh">
		  <h1>云收藏：欢迎使用</h1>
		  <p>
		    欢迎使用微风的龙骑士的云收藏项目！
		  </p>
		</html>
		""".trimIndent())
	}
	
	override fun sendResetPasswordEmail(user: User) = mailSender.sendEmail {
		val url = "http://csntportal/resetPassword?username=${user.username}&resetPasswordCode=${user.resetPasswordCode}"
		
		setFrom(mailProperties.username)
		setTo(user.email)
		setSubject("云收藏：重置你的密码")
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
		setText("""
		<html lang="zh">
		  <h1>云收藏：欢迎使用</h1>
		  <p>
		    已成功重置你的密码。
		  </p>
		</html>
		""".trimIndent())
	}
	
	
	private fun JavaMailSender.sendEmail(encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) = this.send {
		val helper = MimeMessageHelper(it, encoding)
		helper.prepare()
	}
	
	private fun JavaMailSender.sendEmail(multipart: Boolean, encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) = this.send {
		val helper = MimeMessageHelper(it, multipart, encoding)
		helper.prepare()
	}
	
	private fun JavaMailSender.sendEmail(multipartMode: Int, encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) = this.send {
		val helper = MimeMessageHelper(it, multipartMode, encoding)
		helper.prepare()
	}
}
