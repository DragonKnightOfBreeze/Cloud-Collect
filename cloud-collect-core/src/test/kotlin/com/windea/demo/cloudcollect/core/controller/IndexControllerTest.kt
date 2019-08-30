package com.windea.demo.cloudcollect.core.controller

import com.windea.commons.kotlin.extension.*
import com.windea.demo.cloudcollect.core.domain.request.*
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
class IndexControllerTest {
	@Autowired lateinit var mockMvc: MockMvc
	
	@Test
	fun test() {
		print("hello world!!!!!")
	}
	
	@Test
	fun loginByUsernameAndPassword() {
	}
	
	@Test
	fun registerByEmail() {
		val form = EmailRegisterForm("微风的龙骑士", "Windea", "dk_breeze@qq.com", "BreezesLanding")
			.serialize(DataType.Json)
		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_UTF8).content(form))
			.andExpect(status().isOk)
			.andDo(print())
	}
	
	@Test
	fun forgotPassword() {
	}
	
	@Test
	fun activate() {
	}
	
	@Test
	fun resetPassword() {
	}
	
	@Test
	fun lookAroundCollect() {
	}
}
