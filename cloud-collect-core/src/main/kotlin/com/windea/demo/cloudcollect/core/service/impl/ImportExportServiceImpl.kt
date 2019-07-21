package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collect"])
@NotTested("未进行实际测试……")
open class ImportExportServiceImpl(
	private val collectRepository: CollectRepository
) : ImportExportService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun importData(type: DataType, string: String, user: User) {
		//根据指定数据类型读取数据
		val dataSchema = type.loader.fromString(string, DataSchema::class.java)
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
		return type.loader.toString(dataSchema)
	}
}
