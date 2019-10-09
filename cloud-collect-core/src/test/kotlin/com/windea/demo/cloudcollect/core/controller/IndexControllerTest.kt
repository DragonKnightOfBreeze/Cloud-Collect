package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import org.codehaus.jackson.map.*
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
		val form = User(
			nickname = "微风的龙骑士",
			username = "Windea",
			email = "dk_breeze@qq.com",
			password = "BreezesLanding"
		)
		val formJson = ObjectMapper().writeValueAsString(form)
		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_UTF8).content(formJson))
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
