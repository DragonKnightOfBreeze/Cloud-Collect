package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.serialize.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.io.*
import java.nio.file.*

@Service
class DataSerializeServiceImpl(
	private val collectRepository: CollectRepository,
	private val categoryRepository: CategoryRepository,
	private val tagRepository: TagRepository,
	private val serializeProperties: SerializeProperties
) : DataSerializeService {
	override fun importData(dataType: DataType, multipartFile: MultipartFile, user: User) {
		//存储数据文件到本地
		val fileName = "data.${dataType.extension}"
		val fileDirectory = serializeProperties.importPath
		Files.createDirectories(Path.of(fileDirectory))  //尝试创建所有父级目录
		val file = File(fileDirectory, fileName)
		multipartFile.transferTo(file)
		
		try {
			//根据指定数据类型读取数据，这里可以使用泛型列表，并作为具象化泛型
			//考虑使用序列而非列表，进行自顶向下的操作，而非自前往后，需要排除重复名字的收藏
			//需要去除重复的数据
			val collectList = DataSerializer.load<List<CollectSchema>>(file, dataType)
				.distinctBy { it.name }.map { it.toCollect(user) }
			collectRepository.saveAll(collectList)
		} catch(e: Exception) {
			e.printStackTrace()
			throw ImportDataException()
		}
	}
	
	override fun exportData(dataType: DataType, user: User): File {
		try {
			//存储数据文件到本地
			val fileName = "data.${dataType.extension}"
			val fileDirectory = serializeProperties.exportPath
			Files.createDirectories(Path.of(fileDirectory))  //尝试创建所有父级目录
			val file = File(fileDirectory, fileName)
			file.createNewFile()
			
			//从数据库中查找相关数据，然后创建数据约束对象
			//根据指定数据类型转化数据
			val collectSchemaList = collectRepository.findAllByUserId(user.id, Pageable.unpaged())
				.content.map { it.toCollectSchema() }
			DataSerializer.dump(file, collectSchemaList, dataType)
			return file
		} catch(e: Exception) {
			e.printStackTrace()
			throw ExportDataException()
		}
	}
	
	private fun Collect.toCollectSchema() = CollectSchema(
		name = name,
		summary = summary,
		url = url,
		logoUrl = logoUrl,
		categoryName = category?.name ?: "未分类",
		tagNames = tags.map { it.name },
		type = type
	)
	
	private fun CollectSchema.toCollect(user: User) = Collect(
		id = collectRepository.findByNameAndUserId(name, user.id)?.id ?: 0, //可能是添加数据，也可能为更改
		name = name,
		summary = summary,
		url = this.url,
		logoUrl = this.logoUrl,
		category = this.categoryName.let {
			categoryRepository.findByNameAndUserId(it, user.id) ?: categoryRepository.save(Category(name = name, user = user))
		},
		tags = tagNames.map {
			tagRepository.findByName(it) ?: tagRepository.save(Tag(name = name, user = user))
		}.distinctBy { it.name }.toMutableSet(),
		type = this.type,
		user = user
	)
}
