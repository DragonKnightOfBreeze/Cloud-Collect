package com.windea.demo.cloudcollect.core.controller

import com.fasterxml.jackson.databind.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.http.*
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {
	@Autowired lateinit var mockMvc: MockMvc
	
	@Test
	fun test() {
		print("hello world!")
	}
	
	@Test
	fun loginByUsernameAndPassword() {
	}
	
	@Test
	fun registerByEmail() {
		val form = User(
			nickname = "",
			username = "",
			email = "",
			password = ""
		)
		val formJson = ObjectMapper().writeValueAsString(form)
		mockMvc.post("/register") {
			content = formJson
			contentType = MediaType.APPLICATION_JSON
		}.andExpect {
			status { isOk }
		}.andDo {
			print()
		}
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
