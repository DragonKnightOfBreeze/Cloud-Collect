package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

@Api("数据持久化")
@RestController
@RequestMapping("/dataSerialize")
@CrossOrigin
class DataSerializeController(
	private val dataSerializeService: DataSerializeService
) {
	@ApiOperation("从指定格式的文件导入收藏。例如：Xml、Json、Yaml。")
	@PostMapping("/importData")
	fun importData(@RequestParam dataType: DataType, multipartFile: MultipartFile, authentication: Authentication) {
		dataSerializeService.importData(dataType, multipartFile, authentication.toUser())
	}
	
	@ApiOperation("导出收藏到指定格式的文件。例如：Xml、Json、Yaml。")
	@PostMapping("/exportData")
	fun exportData(@RequestParam dataType: DataType): ResponseEntity<ByteArray> {
		val file = dataSerializeService.exportData(dataType)
		//设置响应头，并设置响应体为byte[]类型（也可以是InputStream、Resource等，间接得到byte[]）
		val headers = HttpHeaders()
		headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()
		return ResponseEntity(file.readBytes(), headers, HttpStatus.OK)
	}
}
