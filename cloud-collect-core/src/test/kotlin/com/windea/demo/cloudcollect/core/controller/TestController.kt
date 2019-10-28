package com.windea.demo.cloudcollect.core.controller

import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*

@RestController
class TestController {
	@GetMapping("/test/getPageable")
	fun getPageable(pageable: Pageable): Pageable {
		return pageable
	}
}
