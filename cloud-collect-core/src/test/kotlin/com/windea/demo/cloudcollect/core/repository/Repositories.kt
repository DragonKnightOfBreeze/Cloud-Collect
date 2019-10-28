package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.*
import org.springframework.data.jpa.repository.*

interface FooRepository : JpaRepository<Foo, Long>

interface BarRepository : JpaRepository<Bar, Long> {
	fun existsByFooList(foo: Foo): Boolean
	
	fun existsByFooListContains(foo: Foo): Boolean
	
	fun findByFooListContains(foo: Foo): List<Bar>
	
	fun findByFooList(foo: Foo): List<Bar>
	
	fun findByFooListName(name: String): List<Bar>
}
