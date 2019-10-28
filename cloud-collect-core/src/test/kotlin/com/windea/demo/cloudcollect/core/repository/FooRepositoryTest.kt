package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*

@SpringBootTest
class FooRepositoryTest {
	@Autowired private lateinit var fooRepository: FooRepository
	
	@Test //id属性可空可不空，不空时默认值可为-1或0
	fun testNegId() {
		val foo = Foo(name = "bar")
		val result = fooRepository.save(foo)
		println(result)
	}
}
