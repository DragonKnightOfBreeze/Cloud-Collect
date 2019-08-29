package com.windea.demo.cloudcollect.demo.component

import org.springframework.core.convert.converter.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

/**
 * 字符串到排序对象的转换器。不检查要排序的属性的正确性。
 *
 * 示例格式："", "+name", "+name,-age", "name,age"。
 */
@Component
class StringToSortConverter : Converter<String, Sort> {
	override fun convert(string: String): Sort {
		val orders = string.split(",").filter { it.isNotBlank() }
			.map { s ->
				when {
					s.startsWith("+") -> Sort.Order.asc(s.substring(1))
					s.startsWith("-") -> Sort.Order.desc(s.substring(1))
					else -> Sort.Order.asc(s)
				}
			}
		return Sort.by(orders)
	}
}
