package com.windea.demo.cloudcollect.core.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class BarControllerTest(
	@Autowired private val mockMvc: MockMvc
) {
	@Test
	fun test() {
		mockMvc.get("/test/lazyProp").andDo {
			print()
		}
	}
}
