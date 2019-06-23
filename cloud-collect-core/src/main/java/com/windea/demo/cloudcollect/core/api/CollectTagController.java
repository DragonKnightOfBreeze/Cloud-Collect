package com.windea.demo.cloudcollect.core.api;

import com.windea.demo.cloudcollect.core.service.CollectTagService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/collect-tag")
public class CollectTagController {
	private final CollectTagService service;

	public CollectTagController(CollectTagService service) {
		this.service = service;
	}
}
