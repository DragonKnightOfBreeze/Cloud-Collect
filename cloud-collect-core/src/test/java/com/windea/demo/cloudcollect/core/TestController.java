package com.windea.demo.cloudcollect.core;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
	@GetMapping("/string")
	void getString(@RequestParam String string) {
		System.out.println(string);
	}

	@GetMapping("/pageable")
	void getPageable(@RequestParam Pageable pageable) {
		System.out.println(pageable);
	}
}
