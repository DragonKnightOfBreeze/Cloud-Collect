package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.domain.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.data.repository.*

@SpringBootTest
class JpaTest(
	@Autowired private val fooRepository: FooRepository,
	@Autowired private val barRepository: BarRepository,
	@Autowired private val barService: BarService
) {
	@Test //TESTED
	fun saveCascade1() {
		barRepository.save(Bar(foo = fooRepository.findByIdOrNull(1)!!))
	}
	
	@Test //TESTED
	fun saveCascade2() {
		barRepository.save(Bar(foo = Foo(1, "Foo1")))
	}
	
	//@Test //瞬时态，需要先保存
	//fun saveCascade3() {
	//	barRepository.save(Bar(foo = Foo(0, "Foo1")))
	//}
	
	@Test //TESTED
	fun saveCascadeCollection1() {
		barRepository.save(Bar(fooList = mutableListOf(
			fooRepository.findByIdOrNull(1)!!,
			fooRepository.findByIdOrNull(2)!!
		)))
	}
	
	@Test //TESTED
	fun saveCascadeCollection2() {
		barRepository.save(Bar(fooList = mutableListOf(
			Foo(1, "Foo1"),
			Foo(2, "Foo2")
		)))
	}
	
	//@Test //瞬时态，需要先保存
	//fun saveCascadeCollection3() {
	//	barRepository.save(Bar(fooList = mutableListOf(
	//		Foo(0, "Foo?"),
	//		Foo(0, "Foo?")
	//	)))
	//}
	
	@Test //TESTED
	fun updateCascade1() {
		val bar = barRepository.findByIdOrNull(3)!!
		bar.foo = fooRepository.findByIdOrNull(5)
		barRepository.save(bar)
	}
	
	@Test //TESTED
	fun updateCascade2() {
		val bar = barRepository.findByIdOrNull(3)!!
		bar.foo = Foo(1, "Foo1")
		barRepository.save(bar)
	}
	
	//@Test //瞬时态，需要先保存
	//fun updateCascade3() {
	//	val bar = barRepository.findByIdOrNull(4)!!
	//	bar.foo = Foo(0, "Foo???")
	//	barRepository.save(bar)
	//}
	
	@Test //TESTED
	fun updateCascadeCollection1() {
		val bar = barRepository.findByIdOrNull(6)!!
		bar.fooList += fooRepository.findByIdOrNull(2)!!
		barRepository.save(bar)
	}
	
	@Test //TESTED
	fun updateCascadeCollection2() {
		val bar = barRepository.findByIdOrNull(7)!!
		bar.fooList += Foo(1, "Foo1")
		barRepository.save(bar)
	}
	
	//@Test //瞬时态，需要先保存
	//fun updateCascadeCollection3() {
	//	val bar = barRepository.findByIdOrNull(8)!!
	//	bar.fooList += Foo(0, "Foo1")
	//	barRepository.save(bar)
	//}
	
	//@Test //NO SESSION
	//fun updateCascadeLazy1() {
	//	val bar = barRepository.findByIdOrNull(1)!!
	//	bar.lazyFooList += fooRepository.findByIdOrNull(1)!!
	//	bar.lazyFooList += fooRepository.findByIdOrNull(2)!!
	//	barRepository.save(bar)
	//}
	
	//@Test //NO SESSION
	//fun updateCascadeLazy2() {
	//	val bar = barRepository.findByIdOrNull(1)!!
	//	bar.lazyFooList += Foo(2, "Foo2")
	//	bar.lazyFooList += Foo(4, "Foo4")
	//	barRepository.save(bar)
	//}
	
	@Test //TESTED
	fun findFooByLazy() {
		val barList = barRepository.findByLazyFooListId(1)
		println(barList.isNotEmpty())
	}
	
	//@Test //NO SESSION
	//fun getLazyFooList() {
	//	val bar = barRepository.findByIdOrNull(1)!!
	//	val lazyFooList = bar.lazyFooList.subList(0, 2)
	//	println(lazyFooList)
	//}
	
	@Test
	fun updateCascadeLazy1Tx() {
		//val bar = barRepository.findByIdOrNull(1)!!
		//bar.lazyFooList += fooRepository.findByIdOrNull(1)!!
		//bar.lazyFooList += fooRepository.findByIdOrNull(2)!!
		//println(bar.lazyFooList)
		//barRepository.save(bar)
		barService.updateCascadeLazy1Tx()
		val barList = barRepository.findByLazyFooListId(1)
		println(barList.isNotEmpty())
	}
	
	@Test
	fun updateCascadeLazy2Tx() {
		barService.updateCascadeLazy2Tx()
		val barList = barRepository.findByLazyFooListId(2)
		println(barList.isNotEmpty())
	}
	
	//@Test //TESTED
	//@Transactional
	//fun findFooByLazyTx() {
	//	val bar = barRepository.findByLazyFooListId(1)
	//	println(bar)
	//}
	//
	//@Test
	//@Transactional
	//fun getLazyFooListTx() {
	//	val bar = barRepository.findByIdOrNull(1)!!
	//	val lazyFooList = bar.lazyFooList.subList(0, 2)
	//	println(lazyFooList)
	//}
}
