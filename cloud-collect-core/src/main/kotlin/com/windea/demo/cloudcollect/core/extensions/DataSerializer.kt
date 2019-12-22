@file:Suppress("NOTHING_TO_INLINE")

package com.windea.demo.cloudcollect.core.extensions

import com.fasterxml.jackson.core.type.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.demo.cloudcollect.core.enums.*
import java.io.*

/**数据持久化器。（支持多种数据类型，兼容泛型）*/
object DataSerializer {
	//默认美化输出，并且注册kotlin模块
	private val json by lazy { ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).registerKotlinModule() }
	private val yaml by lazy { YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT).registerKotlinModule() }
	private val xml by lazy { XmlMapper().enable(SerializationFeature.INDENT_OUTPUT).registerKotlinModule() }

	inline fun <reified T> load(string: String, dataType: DataType): T = dataType.toMapper().readValue(string, typeRef<T>())
	
	inline fun dump(data: Any, dataType: DataType): String = dataType.toMapper().writeValueAsString(data)
	
	inline fun <reified T> load(file: File, dataType: DataType): T = dataType.toMapper().readValue(file, typeRef<T>())
	
	inline fun dump(file: File, data: Any, dataType: DataType) = dataType.toMapper().writeValue(file, data)
	
	@PublishedApi
	internal fun DataType.toMapper() = when(this) {
		DataType.JSON -> json
		DataType.YAML -> yaml
		DataType.XML -> xml
	}
	
	@PublishedApi
	internal inline fun <reified T> typeRef() = object : TypeReference<T>() {}
}
