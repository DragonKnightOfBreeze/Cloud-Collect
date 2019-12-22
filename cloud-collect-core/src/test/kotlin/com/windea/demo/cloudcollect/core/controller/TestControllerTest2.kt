package com.windea.demo.cloudcollect.core.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.boot.test.web.client.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest2(
	@Autowired val restTemplate: TestRestTemplate
) {
	@Test
	fun getPageable() {
		restTemplate.getForObject<Any>("/test/getPageable").also { println(it) }
	}
	
	@Test
	fun getPage() {
		restTemplate.getForObject<Any>("/test/getPage").also { println(it) }
	}
}
