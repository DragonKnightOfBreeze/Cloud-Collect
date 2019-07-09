@file:Suppress("UNCHECKED_CAST")

package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.annotation.NotTested
import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.XmlLoader
import com.windea.commons.kotlin.loader.YamlLoader
import com.windea.demo.cloudcollect.core.domain.entity.User
import com.windea.demo.cloudcollect.core.domain.enums.DataType
import com.windea.demo.cloudcollect.core.domain.model.DataSchema
import com.windea.demo.cloudcollect.core.repository.CollectCategoryRepository
import com.windea.demo.cloudcollect.core.repository.CollectRepository
import com.windea.demo.cloudcollect.core.repository.CollectTagRepository
import com.windea.demo.cloudcollect.core.service.ImportExportService
import org.springframework.stereotype.Service

@Service
@NotTested("未进行实际测试……")
class ImportExportServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CollectCategoryRepository,
	private val tagRepository: CollectTagRepository
) : ImportExportService {
	override fun importData(type: DataType, string: String, user: User) {
		//根据指定数据类型读取数据
		val dataSchema = when(type) {
			DataType.XML -> XmlLoader.instance().fromString(string, DataSchema::class.java)
			DataType.JSON -> JsonLoader.instance().fromString(string, DataSchema::class.java)
			DataType.YAML -> YamlLoader.instance().fromString(string, DataSchema::class.java)
		}
		//导入数据时，需要重置相关id，并设置用户为当前用户
		dataSchema.collectList.map { collect ->
			collect.id = null
			collect.tags.map { tag ->
				tag.id = null
				tag.user = user
			}
			collect.category?.let { category ->
				category.id = null
				category.user = user
			}
			collect.user = user
		}
		//存储到数据库中
		collectRepository.saveAll(dataSchema.collectList)
	}
	
	override fun exportData(type: DataType): String {
		//从数据库中查找相关数据，然后创建数据约束对象
		val collectList = collectRepository.findAll()
		val dataSchema = DataSchema(collectList)
		//根据指定数据类型转化数据
		return when(type) {
			DataType.XML -> XmlLoader.instance().toString(dataSchema)
			DataType.JSON -> JsonLoader.instance().toString(dataSchema)
			DataType.YAML -> YamlLoader.instance().toString(dataSchema)
		}
	}
}
