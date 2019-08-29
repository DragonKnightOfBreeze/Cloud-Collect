@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.enums.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*
import java.nio.file.Path
import javax.validation.*

/**收藏的控制器。*/
@Api("收藏")
@RestController
@RequestMapping("/collect")
@CrossOrigin
class CollectController(
	private val service: CollectService,
	private val commentService: CommentService,
	private val userService: UserService,
	private val dataImportExportService: DataImportExportService,
	private val urlCopyService: UrlCopyService,
	private val dataImportExportProperties: DataImportExportProperties
) {
	@ApiOperation("创建自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collect", value = "新的收藏", required = true)
	)
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Valid collect: Collect, bindingResult: BindingResult, authentication: Authentication): Collect {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.create(collect, user)
	}
	
	@ApiOperation("从别人的收藏创建自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "别人的收藏的id", required = true)
	)
	@PostMapping("/createFrom")
	@PreAuthorize("isAuthenticated()")
	fun createFrom(@RequestParam id: Long, authentication: Authentication): Collect {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.createFrom(id, user)
	}
	
	@ApiOperation("删除自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Collect', 'delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("修改自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "collect", value = "修改后的收藏", required = true)
	)
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Collect', 'write')")
	fun modify(@PathVariable id: Long, @RequestBody @Valid collect: Collect, bindingResult: BindingResult): Collect {
		return service.modify(id, collect)
	}
	
	@ApiOperation("修改自己的收藏的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "category", value = "修改后的收藏的分类", required = true))
	@PutMapping("/{id}/category")
	@PreAuthorize("hasPermission(#id, 'Collect', 'write')")
	fun modifyCategory(@PathVariable id: Long, @RequestBody category: CollectCategory): Collect {
		return service.modifyCategory(id, category)
	}
	
	@ApiOperation("修改自己的收藏的标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "tags", value = "修改后的收藏的标签", required = true)
	)
	@PutMapping("/{id}/tags")
	@PreAuthorize("hasPermission(#id, 'Collect', 'write')")
	fun modifyTags(@PathVariable id: Long, @RequestBody tags: MutableSet<CollectTag>): Collect {
		return service.modifyTags(id, tags)
	}
	
	@ApiOperation("修改自己的收藏的类型。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "type", value = "修改后的收藏的类型", required = true)
	)
	@PutMapping("/{id}/type")
	@PreAuthorize("hasPermission(#id, 'CollectCategory', 'write')")
	fun modifyType(@PathVariable id: Long, @RequestBody type: CollectType): Collect {
		return service.modifyType(id, type)
	}
	
	@ApiOperation("点赞某一收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@PutMapping("/{id}/praise")
	fun praise(@PathVariable id: Long, authentication: Authentication): Collect {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.praise(id, user)
	}
	
	@ApiOperation("根据id得到某一收藏。")
	@ApiImplicitParams(ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"))
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Collect {
		return service.findById(id)
	}
	
	@ApiOperation("分页得到某一收藏的所有点赞用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/praiseByUserPage")
	fun getPraiseByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return userService.findAllByPraiseToCollectId(id, pageable)
	}
	
	@ApiOperation("得到某一收藏的点赞用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/praiseByUserCount")
	fun getPraiseByUserCount(@PathVariable id: Long): Long {
		return userService.countByPraiseToCollectId(id)
	}
	
	@ApiOperation("分页得到某一收藏的所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return commentService.findAllBySponsorByUserId(id, pageable)
	}
	
	@ApiOperation("得到某一收藏的评论数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/commentCount")
	fun getCommentCount(@PathVariable id: Long): Long {
		return commentService.countBySponsorByUserId(id)
	}
	
	@ApiOperation("分页得到所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Collect> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("根据名字分页全局查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据名字和用户id分页模糊查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@ApiOperation("根据分类id分页查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "categoryId", value = "分类的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndCategory")
	fun findAllByCategoryId(@RequestParam categoryId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByCategoryId(categoryId, pageable)
	}
	
	@ApiOperation("根据标签id分页查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "tagId", value = "标签的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByTagId")
	fun findAllByTagId(@RequestParam tagId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByTagId(tagId, pageable)
	}
	
	@ApiOperation("根据类型和用户id分页查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "type", value = "收藏的类型", required = true),
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByTypeAndUserId")
	fun findAllByTypeAndUserId(@RequestParam type: CollectType, @RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@ApiOperation("根据用户id和收藏状态分页查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findAllByUserId(userId, pageable)
	}
	
	
	@ApiOperation("从指定格式的文件导入收藏。例如：Xml、Json、Yaml。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "type", value = "数据类型", required = true),
		ApiImplicitParam(name = "multipartFile", value = "上传的文件", required = true)
	)
	@PostMapping("/import")
	@PreAuthorize("isAuthenticated()")
	fun importData(@RequestParam(defaultValue = "YAML") type: DataType, multipartFile: MultipartFile,
		authentication: Authentication) {
		//不检查文件格式是否正确，委托给前端
		val fileName = "${dataImportExportProperties.fileName}.${type.extension}"
		val filePath = Path.of(dataImportExportProperties.path, fileName)
		val file = filePath.toFile()
		multipartFile.transferTo(file)
		val string = file.readText()
		
		//更新数据库
		val user = (authentication.principal as JwtUserDetails).delegateUser
		dataImportExportService.importData(type, string, user)
	}
	
	@ApiOperation("导出收藏到指定格式的文件。例如：Xml、Json、Yaml。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "type", value = "数据类型", required = true)
	)
	@PostMapping("/export")
	@PreAuthorize("isAuthenticated()")
	fun exportData(@RequestParam(defaultValue = "YAML") type: DataType): ResponseEntity<ByteArray> {
		//不在本地缓存文件
		val fileName = "${dataImportExportProperties.fileName}.${type.extension}"
		val filePath = Path.of(dataImportExportProperties.path, fileName)
		val file = filePath.toFile()
		val string = dataImportExportService.exportData(type)
		file.writeText(string)
		
		//设置响应头，并设置响应体为byte[]类型（也可以是InputStream、Resource等，间接得到byte[]）
		val headers = HttpHeaders()
		headers.contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build()
		return ResponseEntity(string.toByteArray(), headers, HttpStatus.OK)
	}
	
	
	@ApiOperation("复制为Html链接。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collect", value = "目标收藏", required = true)
	)
	@PostMapping("/copyAsHtmlUrl")
	fun copyAsHtmlUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsHtmlUrl(collect)
	}
	
	@ApiOperation("复制为Markdown链接。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collect", value = "目标收藏", required = true)
	)
	@PostMapping("/copyAsMdUrl")
	fun copyAsMdUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsMdUrl(collect)
	}
	
	@ApiOperation("复制为Markdown引用链接。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collect", value = "目标收藏", required = true)
	)
	@PostMapping("/copyAsMdRefUrl")
	fun copyAsMdRefUrl(@RequestBody collect: Collect): Pair<String, String> {
		return urlCopyService.copyAsMdRefUrl(collect)
	}
}
