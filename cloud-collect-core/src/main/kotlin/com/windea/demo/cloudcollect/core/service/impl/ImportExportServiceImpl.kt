package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import org.springframework.cache.annotation.*
import org.springframework.stereotype.*
import org.springframework.validation.annotation.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["collect"])
@NotTested("未进行实际测试……")
open class ImportExportServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CollectCategoryRepository,
	private val tagRepository: CollectTagRepository
) : ImportExportService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun importData(type: DataType, string: String, user: User) {
		try {
			//根据指定数据类型读取数据
			val dataSchema = type.loader.fromString(string, DataSchema::class.java)
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
	
	override fun exportData(type: DataType): String {
		try {
			////从数据库中查找相关数据，然后创建数据约束对象
			val collectSchemaList = collectRepository.findAll().map { it.toCollectSchema() }
			val dataSchema = DataSchema(collectSchemaList)
			//根据指定数据类型转化数据
			return type.loader.toString(dataSchema)
		} catch(e: Exception) {
			throw ExportDataException()
		}
	}
	
	private fun Collect.toCollectSchema(): CollectSchema {
		return CollectSchema(
			this.name,
			this.summary,
			this.url,
			this.logoUrl,
			this.category?.name ?: "默认分类",
			this.tags.map { it.name },
			this.type
		)
	}
	
	@Validated
	private fun CollectSchema.toCollect(user: User): Collect {
		val userId = user.id ?: throw UserNotFoundException()
		return Collect(
			name = this.name,
			summary = this.summary,
			url = this.url,
			logoUrl = this.logoUrl,
			category = this.categoryName.let {
				categoryRepository.findByNameAndUserId(it, userId) ?: CollectCategory(name = name, user = user)
			},
			tags = tagNames.map {
				tagRepository.findByNameAndUserId(it, userId) ?: CollectTag(name = name, user = user)
			}.distinctBy { it.name }.toMutableSet(),
			type = this.type,
			user = user
		)
	}
}
