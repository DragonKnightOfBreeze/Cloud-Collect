package com.windea.demo.cloudcollect.demo.component

import com.windea.utility.common.extensions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.core.convert.converter.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

/**
 * 字符串到分页对象的转换器。页面从1开始。
 *
 * 示例格式："", "1,10", "1,10,+name"。
 */
@Component
class StringToPageableConverter : Converter<String, Pageable> {
	@Autowired private lateinit var stringToSortConverter: StringToSortConverter
	
	override fun convert(string: String): Pageable {
		val splitStrings = string.split(",".toRegex(), 3).toTypedArray()
		
		val page = splitStrings.getOrDefault(0, "1").toIntOrDefault(1)
		val size = splitStrings.getOrDefault(1, "10").toIntOrDefault(10)
		val sort = stringToSortConverter.convert(splitStrings.getOrDefault(2, ""))
		
		return PageRequest.of(page - 1, size, sort)
	}
}
