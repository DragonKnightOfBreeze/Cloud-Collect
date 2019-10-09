@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
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
	@ApiOperation("注册用户。")
	@PostMapping("/register")
	@PreAuthorize("isAnonymous()")
	fun register(@RequestBody @Validated(Create::class) user: User, bindingResult: BindingResult): User {
		return userService.register(user)
	}
	
	@ApiOperation("登录用户。")
	@PostMapping("/login")
	@PreAuthorize("isAnonymous()")
	fun login(@RequestBody @Validated form: LoginForm, bindingResult: BindingResult): UserDetailsVo {
		return userService.login(form)
	}
	
	@ApiOperation("激活用户。")
	@PostMapping("/activate")
	fun activate(@RequestBody form: ActivateForm): Boolean {
		return userService.activate(form)
	}
	
	@ApiOperation("忘记用户密码，发送重置密码邮件。")
	@GetMapping("/forgotPassword")
	@PreAuthorize("isAnonymous()")
	fun forgotPassword(@RequestParam username: String) {
		userService.forgotPassword(username)
	}
	
	@ApiOperation("重置用户密码。")
	@PostMapping("/resetPassword")
	fun resetPassword(@RequestBody @Validated form: ResetPasswordForm, bindingResult: BindingResult): Boolean {
		return userService.resetPassword(form)
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
