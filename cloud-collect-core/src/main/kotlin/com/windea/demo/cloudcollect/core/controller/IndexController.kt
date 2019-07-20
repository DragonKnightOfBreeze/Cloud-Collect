@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.domain.view.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/** 首页的控制器。包含登录、注册等操作。*/
@Api("首页")
@RestController
@RequestMapping("/")
@CrossOrigin
class IndexController(
	private val collectService: CollectService, private val userService: UserService
) {
	@ApiOperation("通过用户名&密码登录用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "view", value = "用户名&密码登录视图", required = true)
	)
	@PostMapping("/login", "/loginByUsernameAndPassword")
	fun loginByUsernameAndPassword(@RequestBody @Valid view: UsernamePasswordLoginView, bindingResult: BindingResult): User {
		return userService.loginByUsernameAndPassword(view)
	}
	
	@ApiOperation("通过邮箱注册用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "view", value = "邮箱注册视图", required = true)
	)
	@PostMapping("/register", "/registerByEmail")
	fun registerByEmail(@RequestBody @Valid view: EmailRegisterView, bindingResult: BindingResult): User {
		return userService.registerByEmail(view)
	}
	
	@ApiOperation("激活用户。")
	@PutMapping("/activate")
	fun activate(authentication: Authentication): User {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return userService.activate(user)
	}
	
	@ApiOperation("重置用户密码。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "newPassword", value = "新的密码", required = true)
	)
	@PutMapping("/resetPassword")
	fun resetPassword(@RequestParam newPassword: String, authentication: Authentication): User {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return userService.resetPassword(user, newPassword)
	}
	
	@ApiOperation("随便看看任一收藏。")
	@GetMapping("/lookAroundCollect")
	fun lookAroundCollect(): Collect {
		return collectService.getByRandom()
	}
	
	@ApiOperation("随便看看任一用户信息")
	@GetMapping("/lookAroundUser")
	fun lookAroundUser(): User {
		return userService.getByRandom()
	}
}
