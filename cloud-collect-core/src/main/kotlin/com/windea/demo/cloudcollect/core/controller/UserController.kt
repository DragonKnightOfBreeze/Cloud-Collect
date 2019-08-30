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
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "user", value = "更新后的用户信息", required = true)
	)
	@PutMapping("/{id}")
	fun update(@PathVariable id: Long, @RequestBody @Validated user: User, bindingResult: BindingResult): User {
		return userService.modify(id, user)
	}
	
	@ApiOperation("根据id得到用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): User {
		return userService.findById(id)
	}
	
	@ApiOperation("得到所有用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<User> {
		return userService.findAll(pageable)
	}
	
	@ApiOperation("根据昵称全局模糊查询用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "nickname", value = "昵称", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByNicknameContains")
	fun findAllByNicknameContains(@RequestParam nickname: String, @RequestParam pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContains(nickname, pageable)
	}
	
	@ApiOperation("根据身份全局查询用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "role", value = "身份", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByRole")
	fun findAllByRole(@RequestParam role: Role, @RequestParam pageable: Pageable): Page<User> {
		return userService.findAllByRole(role, pageable)
	}
	
	
	@ApiOperation("得到该用户的关注用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/followToUserCount")
	fun getFollowToUserCount(@PathVariable id: Long): Long {
		return userService.getFollowToUserCount(id)
	}
	
	@ApiOperation("得到该用户的粉丝用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/followByUserCount")
	fun getFollowByUserCount(@PathVariable id: Long): Long {
		return userService.getFollowByUserCount(id)
	}
	
	@ApiOperation("得到该用户的收藏数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return userService.getCollectCount(id)
	}
	
	@ApiOperation("得到该用户的评论数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/commentCount")
	fun getCommentCount(@PathVariable id: Long): Long {
		return userService.getCommentCount(id)
	}
	
	@ApiOperation("得到某一用户的通知数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/noticeCount")
	fun getNoticeCount(@PathVariable id: Long): Long {
		return userService.getNoticeCount(id)
	}
	
	@ApiOperation("得到该用户的所有关注用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/followToUserPage")
	fun getFollowToUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return userService.getFollowToUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有粉丝用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/followByUserPage")
	fun getFollowByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return userService.getFollowByUserPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return userService.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return userService.getCommentPage(id, pageable)
	}
	
	@ApiOperation("得到该用户的所有通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/noticePage")
	fun getNoticePage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Notice> {
		return userService.getNoticePage(id, pageable)
	}
}

