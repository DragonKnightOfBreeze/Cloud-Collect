package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.*
import com.windea.demo.cloudcollect.core.repository.*
import org.springframework.beans.factory.annotation.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
class BarService(
	@Autowired private val fooRepository: FooRepository,
	@Autowired private val barRepository: BarRepository
) {
	@Transactional
	fun updateCascadeLazy1Tx() {
		val bar = barRepository.findByIdOrNull(1)!!
		bar.lazyFooList = listOf(fooRepository.findByIdOrNull(1)!!)
		//bar.lazyFooList += fooRepository.findByIdOrNull(1)!!
		//bar.lazyFooList += fooRepository.findByIdOrNull(2)!!
		println(bar.lazyFooList)
	}
	
	@Transactional
	fun updateCascadeLazy2Tx() {
		val bar = barRepository.findByIdOrNull(1)!!
		bar.lazyFooList += Foo(2, "Foo2")
		bar.lazyFooList += Foo(3, "Foo3")
		println(bar.lazyFooList)
	}
}
