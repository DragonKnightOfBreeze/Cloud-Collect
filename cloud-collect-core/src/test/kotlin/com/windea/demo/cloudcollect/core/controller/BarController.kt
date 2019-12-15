package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.*
import com.windea.demo.cloudcollect.core.repository.*
import org.springframework.data.repository.*
import org.springframework.web.bind.annotation.*

@RestController
class BarController(
	private val barRepository: BarRepository
) {
	@GetMapping("/test/lazyProp")
	fun lazyProp(): MutableList<Foo> {
		val bar = barRepository.findByIdOrNull(1)!!
		val lazyFooList = bar.lazyPropExt()
		return lazyFooList
	}
	
	private fun Bar.lazyPropExt() = this.lazyFooList.subList(0, 2)
}
