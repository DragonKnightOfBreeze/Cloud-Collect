package com.windea.demo.cloudcollect.core

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.cache.annotation.*
import org.springframework.data.jpa.repository.config.*

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
class CloudCollectApplication

fun main(args: Array<String>) {
	runApplication<CloudCollectApplication>(*args)
}
