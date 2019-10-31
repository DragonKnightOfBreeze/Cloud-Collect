package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.serialize.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.io.*
import java.nio.file.*

@Service
@CacheConfig(cacheNames = ["collect"])
class DataSerializeServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CategoryRepository,
	private val tagRepository: TagRepository,
	private val dataSerializeProperties: DataSerializeProperties
) : DataSerializeService {
	override fun importData(dataType: DataType, multipartFile: MultipartFile, user: User) {
		//不检查文件格式是否正确，委托给前端
		val fileName = "${dataSerializeProperties.fileName}.${dataType.extension}"
		val filePath = Path.of(dataSerializeProperties.path, fileName)
		val file = filePath.toFile()
		multipartFile.transferTo(file)
		
		try {
			//根据指定数据类型读取数据，这里可以使用泛型列表，并作为具象化泛型
			//考虑使用序列而非列表，进行自顶向下的操作，而非自前往后，需要排除重复名字的收藏
			//NOTE 需要去除重复的数据
			val collectSchemaList = DataSerializer.load<List<CollectSchema>>(file, dataType)
				.distinctBy { it.name }
			collectSchemaList.forEach {
				//存储到数据库中，分类和标签会一并级联保存
				collectRepository.save(it.toCollect(user))
			}
		} catch(e: Exception) {
			throw ImportDataException()
		}
	}
	
	override fun exportData(dataType: DataType): File {
		//不在本地缓存文件
		val fileName = "${dataSerializeProperties.fileName}.${dataType.extension}"
		val filePath = Path.of(dataSerializeProperties.path, fileName)
		val file = filePath.toFile()
		
		try {
			//从数据库中查找相关数据，然后创建数据约束对象
			val collectSchemaList = collectRepository.findAll().map { it.toCollectSchema() }
			//根据指定数据类型转化数据
			DataSerializer.dump(file, collectSchemaList, dataType)
		} catch(e: Exception) {
			throw ExportDataException()
		}
		return file
	}
	
	private fun Collect.toCollectSchema() = CollectSchema(
		name = name,
		summary = summary,
		url = url,
		logoUrl = logoUrl,
		categoryName = category?.name ?: "默认分类",
		tagNames = tags.map { it.name },
		type = type
	)
	
	private fun CollectSchema.toCollect(user: User) = Collect(
		id = collectRepository.findByNameAndUserId(name, user.id)?.id ?: 0, //NOTE 可能是添加数据，也可能为更改
		name = name,
		summary = summary,
		url = this.url,
		logoUrl = this.logoUrl,
		category = this.categoryName.let {
			categoryRepository.findByNameAndUserId(it, user.id) ?: Category(name = name, user = user)
		},
		tags = tagNames.map {
			tagRepository.findByNameAndUserId(it, user.id) ?: Tag(name = name, user = user)
		}.distinctBy { it.name }.toMutableSet(),
		type = this.type,
		user = user
	)
}
