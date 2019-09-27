package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.serialize.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import org.springframework.cache.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.io.*
import java.nio.file.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collect"])
@NotTested("未进行实际测试……")
class DataImportExportServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CollectCategoryRepository,
	private val tagRepository: CollectTagRepository,
	private val dataImportExportProperties: DataImportExportProperties
) : DataImportExportService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun importData(type: DataType, multipartFile: MultipartFile, user: User) {
		//不检查文件格式是否正确，委托给前端
		val fileName = "${dataImportExportProperties.fileName}.${type.extension}"
		val filePath = Path.of(dataImportExportProperties.path, fileName)
		val file = filePath.toFile()
		multipartFile.transferTo(file)
		
		try {
			//根据指定数据类型读取数据
			val dataSchema = type.loader.fromString(file.readText(), DataSchema::class.java)
			//考虑使用序列而非列表，进行自顶向下的操作，而非自前往后，需要排除重复名字的收藏
			val collectSchemaList = dataSchema.collectSchemaList
			collectSchemaList.forEach {
				//存储到数据库中，分类和标签会一并级联保存
				collectRepository.save(it.toCollect(user))
			}
		} catch(e: Exception) {
			throw ImportDataException()
		}
	}
	
	override fun exportData(type: DataType): File {
		//不在本地缓存文件
		val fileName = "${dataImportExportProperties.fileName}.${type.extension}"
		val filePath = Path.of(dataImportExportProperties.path, fileName)
		val file = filePath.toFile()
		
		try {
			////从数据库中查找相关数据，然后创建数据约束对象
			val collectSchemaList = collectRepository.findAll().map { it.toCollectSchema() }
			val dataSchema = DataSchema(collectSchemaList)
			//根据指定数据类型转化数据
			file.writeText(type.loader.toString(dataSchema))
		} catch(e: Exception) {
			throw ExportDataException()
		}
		return file
	}
	
	private fun Collect.toCollectSchema() = CollectSchema(
		name = this.name,
		summary = this.summary,
		url = this.url,
		logoUrl = this.logoUrl,
		categoryName = this.category?.name ?: "默认分类",
		tagNames = this.tags.map { it.name },
		type = this.type
	)
	
	private fun CollectSchema.toCollect(user: User) = Collect(
		name = this.name,
		summary = this.summary,
		url = this.url,
		logoUrl = this.logoUrl,
		category = this.categoryName.let {
			categoryRepository.findByNameAndUserId(it, user.id!!) ?: CollectCategory(name = name, user = user)
		},
		tags = tagNames.map {
			tagRepository.findByNameAndUserId(it, user.id!!) ?: CollectTag(name = name, user = user)
		}.distinctBy { it.name }.toMutableSet(),
		type = this.type,
		user = user
	)
}
