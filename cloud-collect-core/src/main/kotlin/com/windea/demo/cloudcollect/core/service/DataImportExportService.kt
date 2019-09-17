package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.utility.common.enums.*
import org.springframework.web.multipart.*
import java.io.*

/**数据导入导出的服务。*/
interface DataImportExportService {
	/**从指定格式的文件导入收藏。例如：Xml、Json、Yaml。*/
	fun importData(type: DataType, multipartFile: MultipartFile, user: User)
	
	/**导出收藏到指定格式的文件。例如：Xml、Json、Yaml。*/
	fun exportData(type: DataType): File
}
