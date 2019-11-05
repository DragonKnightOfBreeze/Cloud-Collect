@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.core.*
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
	
	@ApiOperation("关注某一用户。")
	@PutMapping("/{id}/follow")
	fun follow(@PathVariable id: Long, authentication: Authentication) {
		userService.follow(id, authentication.toUser())
	}
	
	@ApiOperation("取消关注某一用户。")
	@PutMapping("/{id}/unfollow")
	fun unfollow(@PathVariable id: Long, authentication: Authentication) {
		userService.unfollow(id, authentication.toUser())
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
	fun findAll(pageable: Pageable): Page<User> {
		return userService.findAll(pageable)
	}
	
	@ApiOperation("根据昵称全局模糊查询用户。")
	@GetMapping("/findAllByNicknameContains")
	fun findAllByNicknameContains(@RequestParam nickname: String, pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContains(nickname, pageable)
	}
	
	@ApiOperation("根据身份全局查询用户。")
	@GetMapping("/findAllByRole")
	fun findAllByRole(@RequestParam role: Role, pageable: Pageable): Page<User> {
		return userService.findAllByRole(role, pageable)
	}
	
	
	@ApiOperation("判断指定用户是否已关注指定用户。")
	@GetMapping("/{id}/isFollowed")
	fun isFollowed(@PathVariable id: Long, authentication: Authentication): Boolean {
		return userService.isFollowed(id, authentication.toUser())
	}
	
	@ApiOperation("得到该用户的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, pageable: Pageable): Page<Collect> {
		return userService.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到该用户点赞的所有收藏。")
	@GetMapping("/{id}/praiseToCollectPage")
	fun getPraiseToCollectPage(@PathVariable id: Long, pageable: Pageable): Page<Collect> {
		return userService.getPraiseToCollectPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有浏览记录。")
	@GetMapping("/{id}/historyPage")
	fun getHistoryPage(@PathVariable id: Long, pageable: Pageable): Page<History> {
		return userService.getHistoryPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有通知。")
	@GetMapping("/{id}/noticePage")
	fun getNoticePage(@PathVariable id: Long, pageable: Pageable): Page<Notice> {
		return userService.getNoticePage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有关注用户。")
	@GetMapping("/{id}/followToUserPage")
	fun getFollowToUserPage(@PathVariable id: Long, pageable: Pageable): Page<User> {
		return userService.getFollowToUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有粉丝用户。")
	@GetMapping("/{id}/followByUserPage")
	fun getFollowByUserPage(@PathVariable id: Long, pageable: Pageable): Page<User> {
		return userService.getFollowByUserPage(id, pageable)
	}
}

