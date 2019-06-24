package com.windea.demo.cloudcollect.core.api;

import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
	private final CommentService service;

	public CommentController(CommentService service) {
		this.service = service;
	}
}
