package com.windea.demo.cloudcollect.core.service

import com.windea.commons.kotlin.enums.*
import com.windea.demo.cloudcollect.core.domain.entity.*

/**
 * 导入/导出数据的服务。
 * @see com.windea.demo.cloudcollect.core.domain.model.DataSchema
 */
interface ImportExportService {
	/**从指定格式的文件导入收藏。例如：Xml、Json、Yaml。*/
	fun importData(type: DataType, string: String, user: User)
	
	/**导出收藏到指定格式的文件。例如：Xml、Json、Yaml。*/
	fun exportData(type: DataType): String
}
