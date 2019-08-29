package com.windea.demo.cloudcollect.demo

import org.junit.*
import org.junit.runner.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.junit4.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
	@Autowired private var mockMvc: MockMvc? = null
	
	//测试转换器
	@Test
	fun test1() {
		println("START:")
		
		mockMvc!!.perform(get("/test/string").param("string", "abc")).andExpect(status().isOk).andDo(print())
		
		mockMvc!!.perform(get("/test/pageable").param("pageable", "")).andExpect(status().isOk).andDo(print())
		
		mockMvc!!.perform(get("/test/pageable").param("pageable", "1,5")).andExpect(status().isOk).andDo(print())
		
		mockMvc!!.perform(get("/test/pageable").param("pageable", "1,5,+name")).andExpect(status().isOk).andDo(print())
		
		mockMvc!!.perform(get("/test/pageable").param("pageable", "1,5,+name,-age")).andExpect(status().isOk).andDo(print())
	}
}
