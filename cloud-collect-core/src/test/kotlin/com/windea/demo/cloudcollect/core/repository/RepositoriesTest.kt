package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.data.repository.*

@SpringBootTest
class RepositoriesTest {
	@Autowired private lateinit var fooRepository: FooRepository
	@Autowired private lateinit var barRepository: BarRepository
	
	@Test //id属性可空可不空，不空时默认值可为-1或0
	fun testNegId() {
		val foo = Foo(name = "bar")
		val result = fooRepository.save(foo)
		println(result)
	}
	
	@Test //TESTED
	fun test2() {
		//val foo1 = fooRepository.save(Foo(name = "foo1"))
		//val foo2 = fooRepository.save(Foo(name = "foo2"))
		//barRepository.save(Bar(fooList = mutableListOf(foo1, foo2)))
		
		val foo1 = fooRepository.findByIdOrNull(5)!!
		
		val result1 = barRepository.existsByFooListContains(foo1)
		assertTrue(result1)
		
		val result11 = barRepository.existsByFooList(foo1)
		assertTrue(result11)
		
		val result2 = barRepository.findByFooListContains(foo1)
		println("result2:$result2")
		
		val result3 = barRepository.findByFooList(foo1)
		println("result3:$result3")
		
		val result4 = barRepository.findByFooListName("foo1")
		println("result4:$result4")
	}
}
