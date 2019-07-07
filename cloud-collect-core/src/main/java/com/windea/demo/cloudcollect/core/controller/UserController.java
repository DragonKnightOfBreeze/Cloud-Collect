package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 用户的控制器。
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}


	//注册、登录等操作委托给首页控制器

	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody User user) {
		service.update(id, user);
	}

	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return service.get(id);
	}

	@GetMapping("/{id}/followToUserPage")
	public Page<User> getFollowToUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getFollowToUserPage(id, pageable);
	}

	@GetMapping("/{id}/followToUserCount")
	public Long getFollowToUserCount(@PathVariable Long id) {
		return service.getFollowToUserCount(id);
	}

	@GetMapping("/{id}/followByUserPage")
	public Page<User> getFollowByUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getFollowByUserPage(id, pageable);
	}

	@GetMapping("/{id}/followByUserCount")
	public Long getFollowByUserCount(@PathVariable Long id) {
		return service.getFollowByUserCount(id);
	}

	@GetMapping("/{id}/collectPage")
	public Page<Collect> getCollectPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCollectPage(id, pageable);
	}

	@GetMapping("/{id}/collectCount")
	public Long getCollectCount(@PathVariable Long id) {
		return service.getCollectCount(id);
	}

	@GetMapping("/{id}/collectCategoryPage")
	public Page<CollectCategory> getCollectCategoryPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCollectCategoryPage(id, pageable);
	}

	@GetMapping("/{id}/collectCategoryCount")
	public Long getCollectCategoryCount(@PathVariable Long id) {
		return service.getCollectCategoryCount(id);
	}

	@GetMapping("/{id}/noticePage")
	public Page<Notice> getNoticePage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getNoticePage(id, pageable);
	}

	@GetMapping("/{id}/noticeCount")
	public Long getNoticeCount(@PathVariable Long id) {
		return service.getNoticeCount(id);
	}

	@GetMapping("/findAll")
	public Page<User> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/findByNickname")
	public Page<User> findByNickname(@RequestParam String nickname, @RequestParam Pageable pageable) {
		return service.findByNickname(nickname, pageable);
	}

	@GetMapping("/findByRole")
	public Page<User> findByRole(@RequestParam Role role, @RequestParam Pageable pageable) {
		return service.findByRole(role, pageable);
	}
}

