package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**数据导入导出的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.data-import-export")
class DataImportExportProperties {
	/**本地路径。*/
	lateinit var path: String
	
	/**数据文件的文件名。*/
	lateinit var dataFileName: String
}
