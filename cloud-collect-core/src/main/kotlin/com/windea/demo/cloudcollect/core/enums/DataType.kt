package com.windea.demo.cloudcollect.core.enums

/**数据的类型。*/
enum class DataType(
	val extension: String
) {
	JSON("json"), YAML("yml"), XML("xml"), PROPERTIES("properties")
}
