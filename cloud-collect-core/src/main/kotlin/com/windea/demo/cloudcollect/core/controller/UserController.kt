@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.data.web.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
class UserController(
	private val userService: UserService
) {
	//注册、登录等操作委托给首页控制器
	
	@ApiOperation("更新用户信息。")
	@PutMapping("/{id}")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) user: User, bindingResult: BindingResult) {
		userService.modify(id, user)
	}
	
	@ApiOperation("根据id得到用户。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): User {
		return userService.findById(id)
	}
	
	@ApiOperation("根据用户名得到用户。")
	@GetMapping("/u/{username}")
	fun findByUsername(@PathVariable username: String): User {
		return userService.findByUsername(username)
	}
	
	@ApiOperation("根据邮箱得到用户。")
	@GetMapping("/e/{email}")
	fun findByEmail(@PathVariable email: String): User {
		return userService.findByEmail(email)
	}
	
	@ApiOperation("得到所有用户。")
	@GetMapping("/findAll")
	fun findAll(@PageableDefault pageable: Pageable): Page<User> {
		return userService.findAll(pageable)
	}
	
	@ApiOperation("根据昵称全局模糊查询用户。")
	@GetMapping("/findAllByNicknameContains")
	fun findAllByNicknameContains(@RequestParam nickname: String, @PageableDefault pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContains(nickname, pageable)
	}
	
	@ApiOperation("根据身份全局查询用户。")
	@GetMapping("/findAllByRole")
	fun findAllByRole(@RequestParam role: Role, @PageableDefault pageable: Pageable): Page<User> {
		return userService.findAllByRole(role, pageable)
	}
	
	
	@ApiOperation("得到该用户的所有关注用户。")
	@GetMapping("/{id}/followToUserPage")
	fun getFollowToUserPage(@PathVariable id: Long, @PageableDefault pageable: Pageable): Page<User> {
		return userService.getFollowToUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有粉丝用户。")
	@GetMapping("/{id}/followByUserPage")
	fun getFollowByUserPage(@PathVariable id: Long, @PageableDefault pageable: Pageable): Page<User> {
		return userService.getFollowByUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PageableDefault pageable: Pageable): Page<Collect> {
		return userService.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有评论。")
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @PageableDefault pageable: Pageable): Page<Comment> {
		return userService.getCommentPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有通知。")
	@GetMapping("/{id}/noticePage")
	fun getNoticePage(@PathVariable id: Long, @PageableDefault pageable: Pageable): Page<Notice> {
		return userService.getNoticePage(id, pageable)
	}
}

