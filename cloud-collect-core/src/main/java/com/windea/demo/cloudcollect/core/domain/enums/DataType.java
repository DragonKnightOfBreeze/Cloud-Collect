package com.windea.demo.cloudcollect.core.domain.enums;

/**
 * 数据类型。
 */
public enum DataType {
	XML("xml"),
	JSON("json"),
	YAML("yml");


	private String extension;

	DataType(String extension) {
		this.extension = extension;
	}


	public String getExtension() {
		return extension;
	}
}
