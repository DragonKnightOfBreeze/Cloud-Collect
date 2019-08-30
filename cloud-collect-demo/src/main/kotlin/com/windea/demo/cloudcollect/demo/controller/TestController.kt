package com.windea.demo.cloudcollect.demo.controller

import com.windea.demo.cloudcollect.demo.domain.*
import com.windea.demo.cloudcollect.demo.repository.*
import com.windea.demo.cloudcollect.demo.validation.*
import org.springframework.data.domain.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController(
	private val repository: EmailRegisterFormRepository
) {
	@GetMapping("/string")
	fun getString(@RequestParam string: String): String {
		println("RESULT:")
		println(string)
		return string
	}
	
	@GetMapping("/pageable")
	fun getPageable(@RequestParam pageable: Pageable): Pageable {
		println("RESULT:")
		println(pageable)
		return pageable
	}
	
	@PostMapping("/register")
	fun register(@Validated(GroupA::class) @RequestBody form: EmailRegisterForm, bindingResult: BindingResult): EmailRegisterForm {
		return repository.save(form)
	}
}
