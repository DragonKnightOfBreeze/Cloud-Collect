package com.windea.demo.cloudcollect.core.domain

import javax.persistence.*

@Entity
data class Foo(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 1,
	val name: String
)
