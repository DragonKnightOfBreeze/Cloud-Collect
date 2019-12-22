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
	@OneToOne
	//@OneToOne(cascade = [CascadeType.PERSIST])
	//@OneToOne(cascade = [CascadeType.MERGE])
	var foo: Foo? = null,
	@ManyToMany(fetch = FetchType.EAGER)
	//@ManyToMany(fetch = FetchType.EAGER,cascade = [CascadeType.PERSIST])
	//@ManyToMany(fetch = FetchType.EAGER,cascade = [CascadeType.MERGE])
	val fooList: MutableList<Foo> = mutableListOf(),
	@ManyToMany
	//@JvmSuppressWildcards
	val lazyFooList: MutableList<Foo> = mutableListOf()
)
