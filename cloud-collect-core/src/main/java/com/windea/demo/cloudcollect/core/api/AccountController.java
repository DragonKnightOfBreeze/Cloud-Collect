package com.windea.demo.cloudcollect.core.api;

import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {
	private final UserService userService;

	public AccountController(UserService userService) {
		this.userService = userService;
	}
}
