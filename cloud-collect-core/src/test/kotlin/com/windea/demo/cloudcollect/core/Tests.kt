package com.windea.demo.cloudcollect.core

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.context.*
import org.springframework.validation.*
import java.util.*

@SpringBootTest
class Tests {
	@Autowired lateinit var validator: Validator
	@Autowired lateinit var messageSource: MessageSource
	
	@Test
	fun test() {
		val msg = messageSource.getMessage("validation.User.UniqueUser", null, Locale.SIMPLIFIED_CHINESE)
		println(msg)
	}
	
	//@Autowired lateinit var dataSerializeProperties: SerializeProperties
	//
	//@Test
	//fun test1() {
	//	//尽管IDE不识别，只要对应的properties数据类：
	//	//注有@ConfigurationProperties和@ConstructorBinding
	//	//不需要@EnableConfigurationProperties和@ConfigurationPropertiesScan
	//	//SpringBoot就会自动注册这个properties数据类为bean
	//	println(dataSerializeProperties)
	//}
	//
	//@Test
	//fun test2() {
	//	//jackson可以将Pair转化为json
	//	val pair = "1" to "2"
	//	println(ObjectMapper().writeValueAsString(pair))
	//}
}
