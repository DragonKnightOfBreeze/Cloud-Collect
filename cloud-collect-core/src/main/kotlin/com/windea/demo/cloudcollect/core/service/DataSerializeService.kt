package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.web.multipart.*
import java.io.*

interface DataSerializeService {
	/**从指定格式的文件导入收藏。例如：Xml、Json、Yaml。*/
	fun importData(dataType: DataType, multipartFile: MultipartFile, user: User)
	
	/**导出收藏到指定格式的文件。例如：Xml、Json、Yaml。*/
	fun exportData(dataType: DataType, user: User): File
}
