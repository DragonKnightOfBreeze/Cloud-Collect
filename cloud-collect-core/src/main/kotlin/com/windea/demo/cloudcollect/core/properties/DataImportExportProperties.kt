package com.windea.demo.cloudcollect.core.properties

import org.springframework.boot.context.properties.*
import org.springframework.context.annotation.*

/**数据导入导出的属性类。*/
@Configuration
@ConfigurationProperties("com.windea.data-import-export")
open class DataImportExportProperties {
	lateinit var path: String
	
	lateinit var fileName: String
}
