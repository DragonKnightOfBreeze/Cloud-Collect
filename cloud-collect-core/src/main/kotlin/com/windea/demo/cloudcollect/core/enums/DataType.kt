package com.windea.demo.cloudcollect.core.enums

/**数据的类型。*/
enum class DataType(
	val extension: String
) {
	Json("json"), Yaml("yml"), Xml("xml"), Properties("properties")
}
