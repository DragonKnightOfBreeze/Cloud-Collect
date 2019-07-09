package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.domain.view.EmailRegisterView;
import com.windea.demo.cloudcollect.core.domain.view.UsernamePasswordLoginView;
import com.windea.demo.cloudcollect.core.service.CollectService;
import com.windea.demo.cloudcollect.core.service.UserService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * TODO 首页的控制器。包含登录、注册等操作。
 */
@Api("首页")
@RestController
@RequestMapping("/")
@CrossOrigin
public class IndexController {
	private final CollectService collectService;
	private final UserService userService;

	public IndexController(CollectService collectService, UserService userService) {
		this.collectService = collectService;
		this.userService = userService;
	}


	@ApiOperation("通过用户名&密码登录用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "view", value = "用户名&密码登录视图", required = true)
	})
	@PostMapping({"/login", "/loginByUsernameAndPassword"})
	public User loginByUsernameAndPassword(@RequestBody @Valid UsernamePasswordLoginView view,
		BindingResult bindingResult) {
		return userService.loginByUsernameAndPassword(view);
	}

	@ApiOperation("通过邮箱注册用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "view", value = "邮箱注册视图", required = true)
	})
	@PostMapping({"/register", "/registerByEmail"})
	public User registerByEmail(@RequestBody @Valid EmailRegisterView view, BindingResult bindingResult) {
		return userService.registerByEmail(view);
	}

	@ApiOperation("激活用户。")
	@PutMapping("/activate")
	public User activate(Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return userService.activate(user);
	}

	@ApiOperation("重置用户密码。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "newPassword", value = "新的密码", required = true)
	})
	@PutMapping("/resetPassword")
	public User resetPassword(@RequestParam String newPassword, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return userService.resetPassword(user, newPassword);
	}

	@ApiOperation("随便看看任一收藏。")
	@GetMapping("/lookAroundCollect")
	public Collect lookAroundCollect() {
		return collectService.getByRandom();
	}

	@ApiOperation("随便看看任一用户信息")
	@GetMapping("/lookAroundUser")
	public User lookAroundUser() {
		return userService.getByRandom();
	}
}
