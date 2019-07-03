package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}
}
