package com.windea.demo.cloudcollect.demo

import com.windea.commons.kotlin.extension.*
import com.windea.demo.cloudcollect.demo.domain.*
import com.windea.utility.common.enums.*
import org.junit.*
import org.junit.runner.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.http.*
import org.springframework.test.context.junit4.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
	@Autowired private lateinit var mockMvc: MockMvc
	
	@Test
	fun registerByEmail() {
		val form = EmailRegisterForm("Windea", "Windea", "dk_breeze@qq.com", "BreezesLanding")
			.serialize(DataType.Json)
		mockMvc.perform(post("/test/register").contentType(MediaType.APPLICATION_JSON_UTF8).content(form))
			.andExpect(status().isOk)
			.andDo(print())
	}
	
	//测试转换器
	@Test
	fun test1() {
		println("START:")
		
		mockMvc.perform(get("/test/string").param("string", "abc")).andExpect(status().isOk).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "")).andExpect(status().isOk).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5")).andExpect(status().isOk).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name")).andExpect(status().isOk).andDo(print())
		
		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name,-age")).andExpect(status().isOk).andDo(print())
	}
}
