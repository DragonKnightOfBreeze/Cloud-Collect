package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/collect")
public class CollectController {
	private final CollectService service;

	public CollectController(CollectService service) {
		this.service = service;
	}
}
