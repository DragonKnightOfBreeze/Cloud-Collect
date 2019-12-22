package com.windea.demo.cloudcollect.core.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
	@Autowired lateinit var mockMvc: MockMvc
	
	@Test
	fun testPageable1() {
		mockMvc.perform(get("/test/getPageable")
		).andDo(print())
	}
	
	@Test
	fun testPageable2() {
		mockMvc.perform(get("/test/getPageable")
			.param("page", "2").param("size", "15")
		).andDo(print())
	}
	
	@Test
	fun testPageable3() {
		mockMvc.perform(get("/test/getPageable")
			.param("page", "2").param("size", "15").param("sort", "name,age")
		).andDo(print())
	}
	
	@Test
	fun testPageable4() {
		//使用Kotlin Dsl
		mockMvc.get("/test/getPageable") {
			param("page", "2")
			param("size", "15")
			param("sort", "name,age,DESC")
		}.andDo {
			print()
		}
	}
}
