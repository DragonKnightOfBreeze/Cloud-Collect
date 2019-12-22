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
	
	@Test
	fun addData() {
		repeat(20) {
			fooRepository.save(Foo(name = "Foo$it"))
		}
		repeat(10) {
			barRepository.save(Bar())
		}
	}
	
	@Test
	fun testCascade() {
		repeat(30) {
			fooRepository.save(Foo(name = "Foo$it"))
		}
		
		//NOTE 当id类型为Long?且为null时可行
		//NOTE 当id类型为Long且为0时可行
		//NOTE Spring会自动根据id判断是save还是update
		//fooRepository.save(Foo(5, "Foo2")).also { println(it) }
		
		
		//barRepository.save(Bar(foo = fooRepository.findByIdOrNull(1)!!)).also { println(it) }
		//
		//barRepository.save(Bar(foo = Foo(1,"Foo1"))).also { println(it) }
		//
		//barRepository.save(Bar(foo = Foo(0, "Foo1"))).also { println(it) }
		//
		//
		//barRepository.save(Bar(fooList =  mutableListOf(
		//	fooRepository.findByIdOrNull(1)!!,
		//	fooRepository.findByIdOrNull(2)!!
		//))).also { println(it) }
		//
		////不行？
		//barRepository.save(Bar(fooList = mutableListOf(
		//	Foo(1,"Foo1"),
		//	Foo(2,"Foo2")
		//))).also { println(it) }
		//
		// barRepository.save(Bar(fooList = mutableListOf(
		//	Foo(0, "Foo?"),
		//	Foo(0, "Foo?")
		//))).also { println(it) }
		//
		//
		//val bar
		//
		////NOTE 保存时，当使用MERGE级联时，级联属性不能包含未持久化也非游离态的属性。
		////val bar2 = barRepository.save(Bar(fooList = mutableListOf(Foo(name="Fooxxxfafjlajselfajl"))))
		////	.also { println(it) }
		//
		//bar.fooList += fooRepository.getOne(5)
		//barRepository.save(bar).also { println(it) }
		//
		//bar.fooList += Foo(name="Fooxxx")
		//barRepository.save(bar).also { println(it) }
		//
		//bar.fooList += Foo(name = "Fooxx112")
		//bar.fooList += fooRepository.getOne(4)
		//barRepository.save(bar).also { println(it) }
		
		//NOTE 更新时替换包含未持久化的级联属性可行
		//bar.fooList = mutableListOf( Foo(name = "Fooxxx"))
		//barRepository.save(bar).also { println(it) }
	}
	
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
