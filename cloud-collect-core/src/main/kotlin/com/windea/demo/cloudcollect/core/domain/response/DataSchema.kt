package com.windea.demo.cloudcollect.core.domain.response

import com.windea.demo.cloudcollect.core.annotation.*
import java.io.*

/**数据概要。用于导入导出数据。*/
@Model
data class DataSchema(
	val collectSchemaList: List<CollectSchema>
) : Serializable
