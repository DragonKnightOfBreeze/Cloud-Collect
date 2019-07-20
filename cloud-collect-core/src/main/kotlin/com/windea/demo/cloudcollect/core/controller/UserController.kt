@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/** 用户的控制器。*/
@Api("用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
class UserController(
	private val service: UserService
) {
	//注册、登录等操作委托给首页控制器
	
	@ApiOperation("更新用户信息。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "user", value = "更新后的用户信息", required = true)
	)
	@PutMapping("/{id}")
	fun update(@PathVariable id: Long, @RequestBody @Valid user: User, bindingResult: BindingResult): User {
		return service.update(id, user)
	}
	
	@ApiOperation("得到用户信息。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	operator fun get(@PathVariable id: Long): User {
		return service.get(id)
	}
	
	@ApiOperation("分页得到某一用户的所有关注用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/followToUserPage")
	fun getFollowToUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return service.getFollowToUserPage(id, pageable)
	}
	
	@ApiOperation("得到某一用户的关注用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/followToUserCount")
	fun getFollowToUserCount(@PathVariable id: Long): Long {
		return service.getFollowToUserCount(id)
	}
	
	@ApiOperation("分页得到某一用户的所有粉丝用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/followByUserPage")
	fun getFollowByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return service.getFollowByUserPage(id, pageable)
	}
	
	@ApiOperation("得到某一用户的粉丝用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/followByUserCount")
	fun getFollowByUserCount(@PathVariable id: Long): Long {
		return service.getFollowByUserCount(id)
	}
	
	@ApiOperation("分页得到某一用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到某一用户的收藏数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return service.getCollectCount(id)
	}
	
	@ApiOperation("得到某一用户的所有收藏分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/collectCategoryPage")
	fun getCollectCategoryPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return service.getCollectCategoryPage(id, pageable)
	}
	
	@ApiOperation("得到某一用户的所有收藏分类数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCategoryCount")
	fun getCollectCategoryCount(@PathVariable id: Long): Long {
		return service.getCollectCategoryCount(id)
	}
	
	@ApiOperation("分页得到某一用户的所有通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/noticePage")
	fun getNoticePage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Notice> {
		return service.getNoticePage(id, pageable)
	}
	
	@ApiOperation("得到某一用户的通知数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/noticeCount")
	fun getNoticeCount(@PathVariable id: Long): Long {
		return service.getNoticeCount(id)
	}
	
	@ApiOperation("分页得到所有用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	fun findAll(@RequestParam pageable: Pageable): Page<User> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("根据昵称分页全局模糊查询用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "nickname", value = "昵称", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByNickname")
	fun findByNickname(@RequestParam nickname: String, @RequestParam pageable: Pageable): Page<User> {
		return service.findByNickname(nickname, pageable)
	}
	
	@ApiOperation("根据身份分页全局查询用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "role", value = "身份", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByRole")
	fun findByRole(@RequestParam role: Role, @RequestParam pageable: Pageable): Page<User> {
		return service.findByRole(role, pageable)
	}
}

