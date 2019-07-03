package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.service.NoticeService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {
	private final NoticeService service;

	public NoticeController(NoticeService service) {
		this.service = service;
	}
}
