package com.windea.demo.cloudcollect.core

import org.springframework.boot.*
import org.springframework.stereotype.*

@Component
class GlobalApplicationRunner : ApplicationRunner {
	override fun run(args: ApplicationArguments?) {
		println("""
			=============================
			CLOUD COLLECT
			==============================
		""".trimIndent())
	}
}
