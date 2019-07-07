package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.domain.view.EmailRegisterView;
import com.windea.demo.cloudcollect.core.domain.view.UsernamePasswordLoginView;
import com.windea.demo.cloudcollect.core.exception.ValidationException;
import com.windea.demo.cloudcollect.core.service.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * TODO 首页的控制器（包含登录、注册等操作）。
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class IndexController {
	private final CollectService collectService;
	private final NoticeService noticeService;
	private final UserService userService;

	public IndexController(CollectService collectService, NoticeService noticeService, UserService userService) {
		this.collectService = collectService;
		this.noticeService = noticeService;
		this.userService = userService;
	}


	@PostMapping(value = "/login", params = {"!type", "type=usernameAndPassword"})
	public User loginByUsernameAndPassword(@RequestBody UsernamePasswordLoginView view, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		return userService.loginByUsernameAndPassword(view);
	}

	@PostMapping(value = "/register", params = {"!type", "type=email"})
	public void registerByEmail(@RequestBody EmailRegisterView view, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		userService.registerByEmail(view);
	}

	@PutMapping("/activate")
	public void activate(Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		userService.activate(user);
	}

	@PutMapping("/resetPassword")
	public void resetPassword(@RequestParam String newPassword, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		userService.resetPassword(user, newPassword);
	}
}
