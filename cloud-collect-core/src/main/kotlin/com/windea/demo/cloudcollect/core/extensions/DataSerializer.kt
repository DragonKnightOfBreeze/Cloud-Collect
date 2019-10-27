@file:Suppress("NOTHING_TO_INLINE")

package com.windea.demo.cloudcollect.core.extensions

import com.fasterxml.jackson.core.type.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.javaprop.*
import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.windea.demo.cloudcollect.core.enums.*
import java.io.*

/**数据持久化器。（支持多种数据类型，兼容泛型）*/
object DataSerializer {
	val json by lazy { ObjectMapper() }
	val yaml by lazy { YAMLMapper() }
	val xml by lazy { XmlMapper() }
	val properties by lazy { JavaPropsMapper() }
	
	inline fun <reified T> load(string: String, dataType: DataType): T = dataType.toMapper().readValue(string, typeRef<T>())
	
	inline fun dump(data: Any, dataType: DataType): String = dataType.toMapper().writeValueAsString(data)
	
	inline fun <reified T> load(file: File, dataType: DataType): T = dataType.toMapper().readValue(file, typeRef<T>())
	
	inline fun dump(file: File, data: Any, dataType: DataType) = dataType.toMapper().writeValue(file, data)
	
	@PublishedApi
	internal fun DataType.toMapper() = when(this) {
		DataType.JSON -> json
		DataType.YAML -> yaml
		DataType.XML -> xml
		DataType.PROPERTIES -> properties
	}
	
	@PublishedApi
	internal inline fun <reified T> typeRef() = object : TypeReference<T>() {}
}
