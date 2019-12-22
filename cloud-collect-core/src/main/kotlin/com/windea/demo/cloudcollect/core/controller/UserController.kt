@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

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
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) user: User, bindingResult: BindingResult) {
		userService.modify(id, user)
	}
	
	@ApiOperation("上传用户头像。")
	@PostMapping("/{id}/uploadAvatar")
	@PreAuthorize("isAuthenticated()")
	fun uploadAvatar(@PathVariable id: Long, @RequestParam multipartFile: MultipartFile): String {
		return userService.uploadAvatar(id, multipartFile)
	}
	
	@ApiOperation("关注某一用户。")
	@PutMapping("/{id}/follow")
	@PreAuthorize("isAuthenticated()")
	fun follow(@PathVariable id: Long) {
		userService.follow(id)
	}
	
	@ApiOperation("取消关注某一用户。")
	@PutMapping("/{id}/unfollow")
	@PreAuthorize("isAuthenticated()")
	fun unfollow(@PathVariable id: Long) {
		userService.unfollow(id)
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
	
	@ApiOperation("根据用户名全局模糊查询用户。")
	@GetMapping("/findAllByUsernameContains")
	fun findAllByUsernameContains(@RequestParam username: String, pageable: Pageable): Page<User> {
		return userService.findAllByUsernameContains(username, pageable)
	}
	
	@ApiOperation("根据邮箱全局模糊查询用户。")
	@GetMapping("/findAllByEmailContains")
	fun findAllByEmailContains(@RequestParam email: String, pageable: Pageable): Page<User> {
		return userService.findAllByEmailContains(email, pageable)
	}
	
	@ApiOperation("根据身份全局查询用户。")
	@GetMapping("/findAllByRole")
	fun findAllByRole(@RequestParam role: Role, pageable: Pageable): Page<User> {
		return userService.findAllByRole(role, pageable)
	}
	
	@ApiOperation("根据关注用户id查询用户。")
	@GetMapping("/findAllByFollowToUserId")
	fun findAllByFollowToUserId(@RequestParam followToUserId: Long, pageable: Pageable): Page<User> {
		return userService.findAllByFollowToUserId(followToUserId, pageable)
	}
	
	@ApiOperation("根据昵称和关注用户id模糊查询用户。")
	@GetMapping("/findAllByNicknameContainsAndFollowToUserId")
	fun findAllByNicknameContainsAndFollowToUserId(@RequestParam nickname: String, @RequestParam followToUserId: Long, pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContainsAndFollowToUserId(nickname, followToUserId, pageable)
	}
	
	/**根据粉丝用户id查询用户。*/
	@ApiOperation("根据粉丝用户id查询用户。")
	@GetMapping("/findAllByFollowByUserId")
	fun findAllByFollowByUserId(@RequestParam followByUserId: Long, pageable: Pageable): Page<User> {
		return userService.findAllByFollowByUserId(followByUserId, pageable)
	}
	
	@ApiOperation("根据昵称和粉丝用户id模糊查询用户。")
	@GetMapping("/findAllByNicknameContainsAndFollowByUserId")
	fun findAllByNicknameContainsAndFollowByUserId(@RequestParam nickname: String, @RequestParam followByUserId: Long, pageable: Pageable): Page<User> {
		return userService.findAllByNicknameContainsAndFollowByUserId(nickname, followByUserId, pageable)
	}
	
	@ApiOperation("根据点赞收藏id查询用户。")
	@GetMapping("/findAllByPraiseToCollectId")
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User> {
		return userService.findAllByPraiseToCollectId(praiseToCollectId, pageable)
	}
}

