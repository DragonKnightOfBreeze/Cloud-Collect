package com.windea.demo.cloudcollect.core.extensions

import com.windea.demo.cloudcollect.core.enums.*
import org.junit.*

class DataSerializerTest {
	@Test //不考虑泛型的情况下，能够正常工作
	fun test1() {
		val string = """
			{"name": "Windea", "weapon": "BreezesLanding"}
		""".trimIndent()
		val jsonData = DataSerializer.load<Map<String, String>>(string, DataType.JSON)
		println(jsonData::class.java)
		println(jsonData)
		
		val jsonString = DataSerializer.dump(jsonData, DataType.JSON)
		println(jsonString)
	}
	
	@Test
	fun test2() {
		val string = """
			{"name": "Windea", "weapon": "BreezesLanding"}
		""".trimIndent()
		val jsonData = DataSerializer.load<Person>(string, DataType.JSON)
		println(jsonData::class.java)
		println(jsonData)
		
		val jsonString = DataSerializer.dump(jsonData, DataType.JSON)
		println(jsonString)
	}
	
	@Test
	fun test3() {
		val string = """
			[
				{"name": "Windea", "weapon": "BreezesLanding"},
				{"name": "Avera", "weapon": "WhiteMistGreatSword"}
			]
		""".trimIndent()
		val jsonData = DataSerializer.load<List<Person>>(string, DataType.JSON)
		println(jsonData::class.java)
		println(jsonData)
		
		val jsonString = DataSerializer.dump(jsonData, DataType.JSON)
		println(jsonString)
	}
}


data class Person(
	val name: String = "",
	val weapon: String = ""
)
