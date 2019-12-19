package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*

/**数据导入导出的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.serialize")
data class SerializeProperties(
	val rootUrl: String,
	val uploadUrl: String,
	val exportUrl: String,
	val rootPath: String,
	val uploadPath: String,
	val exportPath: String,
	val importPath: String
)
