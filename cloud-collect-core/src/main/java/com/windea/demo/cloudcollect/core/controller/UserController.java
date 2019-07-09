package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.service.UserService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户的控制器。
 */
@Api("用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}


	//注册、登录等操作委托给首页控制器

	@ApiOperation("更新用户信息。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "user", value = "更新后的用户信息", required = true)
	})
	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
		return service.update(id, user);
	}

	@ApiOperation("得到用户信息。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到某一用户的所有关注用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/followToUserPage")
	public Page<User> getFollowToUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getFollowToUserPage(id, pageable);
	}

	@ApiOperation("得到某一用户的关注用户数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/followToUserCount")
	public Long getFollowToUserCount(@PathVariable Long id) {
		return service.getFollowToUserCount(id);
	}

	@ApiOperation("分页得到某一用户的所有粉丝用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/followByUserPage")
	public Page<User> getFollowByUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getFollowByUserPage(id, pageable);
	}

	@ApiOperation("得到某一用户的粉丝用户数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/followByUserCount")
	public Long getFollowByUserCount(@PathVariable Long id) {
		return service.getFollowByUserCount(id);
	}

	@ApiOperation("分页得到某一用户的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/collectPage")
	public Page<Collect> getCollectPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCollectPage(id, pageable);
	}

	@ApiOperation("得到某一用户的收藏数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/collectCount")
	public Long getCollectCount(@PathVariable Long id) {
		return service.getCollectCount(id);
	}

	@ApiOperation("得到某一用户的所有收藏分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/collectCategoryPage")
	public Page<CollectCategory> getCollectCategoryPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCollectCategoryPage(id, pageable);
	}

	@ApiOperation("得到某一用户的所有收藏分类数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/collectCategoryCount")
	public Long getCollectCategoryCount(@PathVariable Long id) {
		return service.getCollectCategoryCount(id);
	}

	@ApiOperation("分页得到某一用户的所有通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/noticePage")
	public Page<Notice> getNoticePage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getNoticePage(id, pageable);
	}

	@ApiOperation("得到某一用户的通知数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/noticeCount")
	public Long getNoticeCount(@PathVariable Long id) {
		return service.getNoticeCount(id);
	}

	@ApiOperation("分页得到所有用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<User> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("根据昵称分页全局模糊查询用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "nickname", value = "昵称", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByNickname")
	public Page<User> findByNickname(@RequestParam String nickname, @RequestParam Pageable pageable) {
		return service.findByNickname(nickname, pageable);
	}

	@ApiOperation("根据身份分页全局查询用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "role", value = "身份", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByRole")
	public Page<User> findByRole(@RequestParam Role role, @RequestParam Pageable pageable) {
		return service.findByRole(role, pageable);
	}
}

