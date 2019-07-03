package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.service.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class IndexController {
	private final CollectService collectService;
	private final NoticeService noticeService;
	private final UserService userService;

	public IndexController(CollectService collectService, NoticeService noticeService, UserService userService) {
		this.collectService = collectService;
		this.noticeService = noticeService;
		this.userService = userService;
	}
}
