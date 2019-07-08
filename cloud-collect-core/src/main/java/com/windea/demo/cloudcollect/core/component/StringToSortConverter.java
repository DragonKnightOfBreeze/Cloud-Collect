package com.windea.demo.cloudcollect.core.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 字符串到排序对象的转化器。
 * <p>示例格式："", "+name", "+name,-age", "name,age"。
 */
@Component
public class StringToSortConverter implements Converter<String, Sort> {
	@Override
	public Sort convert(String string) {
		var orders = Arrays.stream(string.split(","))
			.map(String::trim).filter(StringUtils::hasText)
			.map(s -> {
				if(s.startsWith("+")) {
					return Sort.Order.asc(s.substring(1));
				} else if(s.startsWith("-")) {
					return Sort.Order.desc(s.substring(1));
				} else {
					return Sort.Order.asc(s);
				}
			})
			.collect(Collectors.toList());

		return Sort.by(orders);
	}
}
