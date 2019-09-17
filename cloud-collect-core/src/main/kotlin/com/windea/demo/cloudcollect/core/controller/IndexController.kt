@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

/**首页的控制器。包含登录、注册等操作。*/
@Api("首页")
@RestController
@RequestMapping("/")
@CrossOrigin
class IndexController(
	private val collectService: CollectService,
	private val userService: UserService
) {
	@ApiOperation("通过邮箱注册用户。返回值中包含激活码。")
	@PostMapping("/register", "/registerByEmail")
	@PreAuthorize("isAnonymous()")
	fun registerByEmail(@RequestBody @Validated form: EmailRegisterForm, bindingResult: BindingResult): User {
		return userService.registerByEmail(form)
	}
	
	@ApiOperation("激活用户。")
	@PutMapping("/activate")
	fun activate(@RequestParam username: String, @RequestParam activateCode: String) {
		userService.activate(username, activateCode)
	}
	
	@ApiOperation("通过用户名&密码登录用户。")
	@PostMapping("/login", "/loginByUsernameAndPassword")
	@PreAuthorize("isAnonymous()")
	fun loginByUsernameAndPassword(@RequestBody @Validated form: UsernamePasswordLoginForm, bindingResult: BindingResult): JwtUserDetails {
		return userService.loginByUsernameAndPassword(form)
	}
	
	@ApiOperation("忘记用户密码，发送重置密码邮件。返回值中包含忘记密码验证码。")
	@PostMapping("/forgotPassword")
	@PreAuthorize("isAnonymous()")
	fun forgotPassword(@RequestParam username: String) {
		userService.forgotPassword(username)
	}
	
	@ApiOperation("重置用户密码。")
	@PutMapping("/resetPassword")
	fun resetPassword(@Validated @RequestBody form: ResetPasswordForm, bindingResult: BindingResult, @RequestParam resetPasswordCode: String) {
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
