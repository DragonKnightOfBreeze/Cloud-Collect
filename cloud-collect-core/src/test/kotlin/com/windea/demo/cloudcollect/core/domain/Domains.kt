package com.windea.demo.cloudcollect.core.domain

import javax.persistence.*

@Entity
data class Foo(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	val name: String
)

@Entity
data class Bar(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	@OneToMany(fetch = FetchType.EAGER)
	val fooList: MutableList<Foo> = mutableListOf()
)
