package com.windea.demo.cloudcollect.core.controller

import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.util.*
import org.springframework.web.bind.annotation.*

@RestController
class TestController {
	@GetMapping("/test/getPageable")
	fun getPageable(pageable: Pageable): ResponseEntity<Pageable> {
		return ResponseEntity(
			pageable,
			HttpHeaders(LinkedMultiValueMap(mapOf("sort" to listOf(pageable.sort.toString())))),
			HttpStatus.OK
		)
	}
	
	@GetMapping("/test/getPage")
	fun getPage(): Page<List<String>> {
		return PageImpl<List<String>>(listOf(listOf("1"), listOf("2")))
	}
}
