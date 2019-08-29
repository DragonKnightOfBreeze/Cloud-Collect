@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/**首页的控制器。包含登录、注册等操作。*/
@Api("首页")
@RestController
@RequestMapping("/")
@CrossOrigin
class IndexController(
	private val collectService: CollectService,
	private val userService: UserService,
	private val emailService: EmailService
) {
	@ApiOperation("通过用户名&密码登录用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "view", value = "用户名&密码登录视图", required = true)
	)
	@PostMapping("/login", "/loginByUsernameAndPassword")
	@PreAuthorize("isAnonymous()")
	fun loginByUsernameAndPassword(@RequestBody @Valid view: UsernamePasswordLoginForm, bindingResult: BindingResult): User {
		return userService.loginByUsernameAndPassword(view).also {
			//成功注册后，发送激活邮件
			emailService.sendActivateEmail(it)
		}
	}
	
	@ApiOperation("通过邮箱注册用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "view", value = "邮箱注册视图", required = true)
	)
	@PostMapping("/register", "/registerByEmail")
	@PreAuthorize("isAnonymous()")
	fun registerByEmail(@RequestBody @Valid view: EmailRegisterForm, bindingResult: BindingResult): User {
		return userService.registerByEmail(view).also {
			////发送激活邮件
			//emailService.sendActivateEmail(it)
			//发送欢迎邮件
			emailService.sendHelloEmail(it)
		}
	}
	
	@ApiOperation("忘记用户密码，发送重置密码邮件。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "username", value = "用户名", required = true)
	)
	@PostMapping("/forgotPassword")
	@PreAuthorize("isAnonymous()")
	fun forgotPassword(@RequestParam username: String): User {
		return userService.forgotPassword(username).also {
			//发送重置密码邮件
			emailService.sendResetPasswordEmail(it)
		}
	}
	
	@ApiOperation("激活用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "username", value = "用户名", required = true),
		ApiImplicitParam(name = "activateCode", value = "激活码", required = true)
	)
	@PutMapping("/activate")
	fun activate(@RequestParam username: String, @RequestParam activateCode: String): User? {
		return userService.activate(username, activateCode)?.also {
			//发送欢迎邮件
			emailService.sendHelloEmail(it)
		}
	}
	
	@ApiOperation("重置用户密码。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "username", value = "用户名", required = true),
		ApiImplicitParam(name = "password", value = "新的密码", required = true),
		ApiImplicitParam(name = "resetPasswordCode", value = "重置密码的识别码", required = true)
	)
	@PutMapping("/resetPassword")
	fun resetPassword(@RequestParam username: String, @RequestParam password: String, @RequestParam resetPasswordCode: String): User? {
		return userService.resetPassword(username, password, resetPasswordCode)?.also {
			//发送重置密码成功邮件
			emailService.sendResetPasswordSuccessEmail(it)
		}
	}
	
	@ApiOperation("随便看看任一收藏。")
	@GetMapping("/lookAroundCollect")
	fun lookAroundCollect(): Collect {
		return collectService.findByRandom()
	}
}
