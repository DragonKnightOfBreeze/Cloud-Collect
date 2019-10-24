package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*

@Api("发送邮件")
@RestController
@RequestMapping("/email")
@CrossOrigin
class EmailController(
	private val emailService: EmailService
) {
	//NOTE 邮件的发送交由前端，在发送相应的请求且成功后，发送对应的邮件发送请求
	@ApiOperation("发送激活邮件。")
	@GetMapping("/sendActiveEmail")
	@PreAuthorize("isAuthenticated()")
	fun sendActiveEmail(@RequestParam activateCode: String, authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return emailService.sendActivateEmail(activateCode, user)
	}
	
	@ApiOperation("发送欢迎邮件。")
	@GetMapping("/sendHelloEmail")
	@PreAuthorize("isAuthenticated()")
	fun sendHelloEmail(authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return emailService.sendHelloEmail(user)
	}
	
	@ApiOperation("发送重置密码邮件。")
	@GetMapping("/sendResetPasswordEmail")
	@PreAuthorize("isAuthenticated()")
	fun sendResetPasswordEmail(@RequestParam resetPasswordCode: String, authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return emailService.sendResetPasswordEmail(resetPasswordCode, user)
	}
	
	@ApiOperation("发送重置密码成功邮件。")
	@GetMapping("/sendResetPasswordSuccessEmail")
	@PreAuthorize("isAuthenticated()")
	fun sendResetPasswordSuccessEmail(authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return emailService.sendResetPasswordSuccessEmail(user)
	}
}
