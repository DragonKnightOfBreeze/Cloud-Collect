package com.windea.demo.cloudcollect.core

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
class TestControllerTests {
	@Autowired private val mockMvc: MockMvc? = null
	
	//测试转换器
	@Test
	fun test1() {
		mockMvc!!.perform(get("/test/string").param("string", "abc")).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "")).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5")).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name")).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name,-age")).andDo(print())
	}
}
