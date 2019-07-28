package com.windea.demo.cloudcollect.core.domain.model

import com.windea.demo.cloudcollect.core.domain.enums.*

/**数据概要。用于导入导出数据。*/
data class DataSchema(
	val collectSchemaList: List<CollectSchema>
)

/**收藏的概要。用于导入导出数据。*/
data class CollectSchema(
	val name: String,
	val summary: String,
	val url: String,
	val logoUrl: String,
	val categoryName: String,
	val tagNames: List<String>,
	val type: CollectType
)
