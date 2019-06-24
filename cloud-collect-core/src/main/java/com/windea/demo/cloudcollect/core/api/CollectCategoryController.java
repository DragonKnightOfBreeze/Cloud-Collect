package com.windea.demo.cloudcollect.core.api;

import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/collect-category")
public class CollectCategoryController {
	private final CollectCategoryService service;

	public CollectCategoryController(CollectCategoryService service) {
		this.service = service;
	}
}
