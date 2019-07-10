package com.windea.demo.cloudcollect.core.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.windea.commons.kotlin.extension.CollectionExtensionKt.getOrDefault;
import static com.windea.commons.kotlin.extension.StringExtensionKt.toIntOrDefault;

/**
 * 字符串到分页对象的转换器。页面从1开始。
 * <p>示例格式："", "1,10", "1,10,+name"。
 */
@Component
public final class StringToPageableConverter implements Converter<String, Pageable> {
	private final StringToSortConverter stringToSortConverter;

	public StringToPageableConverter(StringToSortConverter stringToSortConverter) {
		this.stringToSortConverter = stringToSortConverter;
	}


	@Override
	public Pageable convert(String string) {
		var splitStrings = string.split(",", 3);

		var page = toIntOrDefault(getOrDefault(splitStrings, 0, "1"), 1);
		var size = toIntOrDefault(getOrDefault(splitStrings, 1, "10"), 10);
		var sort = stringToSortConverter.convert(getOrDefault(splitStrings, 2, ""));

		return PageRequest.of(page - 1, size, sort);
	}
}
