package com.windea.demo.cloudcollect.core.domain.serialize

import com.windea.demo.cloudcollect.core.annotation.*
import com.windea.demo.cloudcollect.core.enums.*
import java.io.*

/**收藏的概要。用于导入导出数据。*/
@Domain
data class CollectSchema(
	val name: String,
	val summary: String,
	val url: String,
	val logoUrl: String,
	val categoryName: String,
	val tagNames: List<String>,
	val type: CollectType
) : Serializable
