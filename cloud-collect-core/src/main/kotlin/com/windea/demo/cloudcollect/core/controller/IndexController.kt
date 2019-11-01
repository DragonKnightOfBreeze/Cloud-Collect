@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

//包含登录注册功能，以及随便看看功能
@Api("首页")
@RestController
@RequestMapping("/")
@CrossOrigin
class IndexController(
	private val collectService: CollectService,
	private val userService: UserService
) {
	@ApiOperation("登录用户。")
	@PostMapping("/login")
	fun login(@RequestBody @Validated form: LoginForm, bindingResult: BindingResult): UserDetailsVo {
		return userService.login(form)
	}
	
	@ApiOperation("注册用户。")
	@PostMapping("/register")
	fun register(@RequestBody @Validated(Create::class) user: User, bindingResult: BindingResult) {
		userService.register(user)
	}
	
	@ApiOperation("激活用户。")
	@PostMapping("/activate")
	fun activate(@RequestParam username: String, @RequestParam activateCode: String) {
		userService.activate(username, activateCode)
	}
	
	@ApiOperation("忘记用户密码。")
	@PostMapping("/forgotPassword")
	fun forgotPassword(@RequestParam username: String) {
		userService.forgotPassword(username)
	}
	
	@ApiOperation("重置用户密码。")
	@PostMapping("/resetPassword")
	fun resetPassword(@RequestBody @Validated form: ResetPasswordForm, bindingResult: BindingResult,
		@RequestParam resetPasswordCode: String) {
		userService.resetPassword(form, resetPasswordCode)
	}
	
	
	@ApiOperation("随便看看任一收藏。")
	@GetMapping("/lookAroundCollect")
	fun lookAroundCollect(): Collect {
		return collectService.findByRandom()
	}
	
	@ApiOperation("随便看看任一用户。")
	@GetMapping("/lookAroundUser")
	fun lookAroundUser(): User {
		return userService.findByRandom()
	}
}
