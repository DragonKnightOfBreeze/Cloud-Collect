package com.windea.demo.cloudcollect.core.controller

import org.junit.*
import org.junit.runner.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.junit4.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
	@Autowired lateinit var mockMvc: MockMvc
	
	@Autowired private lateinit var testController: TestController
	
	@Test
	fun testPageable() {
		mockMvc.perform(get("/test/getPageable")
			//.param("page","2").param("size","15")
		).andDo(print())
	}
}
