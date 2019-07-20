package com.windea.demo.cloudcollect.core

import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController {
	@GetMapping("/string")
	internal fun getString(@RequestParam string: String) {
		println(string)
	}
	
	@GetMapping("/pageable")
	internal fun getPageable(@RequestParam pageable: Pageable) {
		println(pageable)
	}
}
