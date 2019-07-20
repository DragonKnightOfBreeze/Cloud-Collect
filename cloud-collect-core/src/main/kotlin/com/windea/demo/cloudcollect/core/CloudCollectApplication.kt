package com.windea.demo.cloudcollect.core

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*

@SpringBootApplication
open class CloudCollectApplication

fun main(args: Array<String>) {
	runApplication<CloudCollectApplication>(*args)
}
