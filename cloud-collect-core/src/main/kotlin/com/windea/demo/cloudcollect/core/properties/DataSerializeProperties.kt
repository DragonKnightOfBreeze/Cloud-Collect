package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*

/**数据导入导出的属性类。*/
@ConstructorBinding
@ConfigurationProperties("cloud-collect.data-serialize")
data class DataSerializeProperties(
	val path: String,
	val fileName: String
)
