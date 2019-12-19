package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.core.io.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*
import java.net.*

@Api("数据持久化")
@RestController
@RequestMapping("/dataSerialize")
@CrossOrigin
class DataSerializeController(
	private val dataSerializeService: DataSerializeService
) {
	@ApiOperation("从指定格式的文件导入收藏。例如：Xml、Json、Yaml。")
	@PostMapping("/importData")
	@PreAuthorize("isAuthenticated()")
	fun importData(@RequestParam dataType: DataType, @RequestParam multipartFile: MultipartFile) {
		dataSerializeService.importData(dataType, multipartFile, currentUser!!)
	}
	
	@ApiOperation("导出收藏到指定格式的文件。例如：Xml、Json、Yaml。")
	@GetMapping("/exportData")
	@PreAuthorize("isAuthenticated()")
	fun exportData(@RequestParam dataType: DataType): ResponseEntity<Resource> {
		val file = dataSerializeService.exportData(dataType, currentUser!!)
		val encodedFileName = URLEncoder.encode(file.name, "UTF-8")
		
		//设置响应头，并设置响应体为byte[]类型（也可以是InputStream、Resource等，间接得到byte[]）
		val headers = HttpHeaders()
		headers.contentType = MediaType.parseMediaType("application/x-download;charset=utf-8")
		headers.contentDisposition = ContentDisposition.builder("attachment").filename(encodedFileName).build()
		return ResponseEntity.ok().headers(headers).body(InputStreamResource(file.inputStream()))
	}
}
