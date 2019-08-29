package com.windea.demo.cloudcollect.demo.controller

import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController {
	@GetMapping("/string")
	fun getString(@RequestParam string: String): String {
		println("RESULT:")
		println(string)
		return string
	}
	
	@GetMapping("/pageable")
	fun getPageable(@RequestParam pageable: Pageable): Pageable {
		println("RESULT:")
		println(pageable)
		return pageable
	}
}
