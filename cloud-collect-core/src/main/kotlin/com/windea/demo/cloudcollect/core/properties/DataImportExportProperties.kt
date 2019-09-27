package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

/**数据导入导出的属性类。*/
@Component
@ConfigurationProperties("cloud-collect.data-import-export")
class DataImportExportProperties {
	/**本地路径。*/
	lateinit var path: String
	/**数据文件的文件名。*/
	lateinit var fileName: String
}
