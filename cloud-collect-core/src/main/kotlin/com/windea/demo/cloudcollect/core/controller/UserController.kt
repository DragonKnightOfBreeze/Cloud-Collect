@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

/**用户的控制器。*/
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
	fun update(@PathVariable id: Long, @RequestBody @Validated user: User, bindingResult: BindingResult): User {
		return userService.modify(id, user)
	}
	
	@ApiOperation("根据id得到用户。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): User {
		return userService.findById(id)
	}
	
	@ApiOperation("得到所有用户。")
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<User> {
		return userService.findAll(pageable)
	}
	
	@ApiOperation("根据昵称全局模糊查询用户。")
	@GetMapping("/findAllByNicknameContains")
	fun findAllByNicknameContains(@RequestParam nickname: String, @RequestParam pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContains(nickname, pageable)
	}
	
	@ApiOperation("根据身份全局查询用户。")
	@GetMapping("/findAllByRole")
	fun findAllByRole(@RequestParam role: Role, @RequestParam pageable: Pageable): Page<User> {
		return userService.findAllByRole(role, pageable)
	}
	
	
	@ApiOperation("得到该用户的关注用户数量。")
	@GetMapping("/{id}/followToUserCount")
	fun getFollowToUserCount(@PathVariable id: Long): Long {
		return userService.getFollowToUserCount(id)
	}
	
	@ApiOperation("得到该用户的粉丝用户数量。")
	@GetMapping("/{id}/followByUserCount")
	fun getFollowByUserCount(@PathVariable id: Long): Long {
		return userService.getFollowByUserCount(id)
	}
	
	@ApiOperation("得到该用户的收藏数量。")
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return userService.getCollectCount(id)
	}
	
	@ApiOperation("得到该用户的评论数量。")
	@GetMapping("/{id}/commentCount")
	fun getCommentCount(@PathVariable id: Long): Long {
		return userService.getCommentCount(id)
	}
	
	@ApiOperation("得到某一用户的通知数量。")
	@GetMapping("/{id}/noticeCount")
	fun getNoticeCount(@PathVariable id: Long): Long {
		return userService.getNoticeCount(id)
	}
	
	@ApiOperation("得到该用户的所有关注用户。")
	@GetMapping("/{id}/followToUserPage")
	fun getFollowToUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return userService.getFollowToUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有粉丝用户。")
	@GetMapping("/{id}/followByUserPage")
	fun getFollowByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return userService.getFollowByUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return userService.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有评论。")
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return userService.getCommentPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有通知。")
	@GetMapping("/{id}/noticePage")
	fun getNoticePage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Notice> {
		return userService.getNoticePage(id, pageable)
	}
}

