package com.windea.demo.cloudcollect.core.domain.model

import com.windea.demo.cloudcollect.core.domain.entity.*
import java.io.*

/**
 * 数据约束。用于导入/导出数据。
 */
class DataSchema(
	val collectList: List<Collect>
) : Serializable
