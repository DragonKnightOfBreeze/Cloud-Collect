@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.commons.kotlin.enums.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*
import java.nio.file.*
import java.nio.file.Path
import javax.validation.*

/** 收藏的控制器。*/
@Api("收藏")
@RequestMapping("/collect")
@RestController
@CrossOrigin
class CollectController(
	private val service: CollectService,
	private val ieService: ImportExportService
) {
	@ApiOperation("创建自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collect", value = "新的收藏", required = true)
	)
	@PostMapping("/create")
	fun create(@RequestBody @Valid collect: Collect, bindingResult: BindingResult, authentication: Authentication): Collect {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.create(collect, user)
	}
	
	@ApiOperation("从别人的收藏创建自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "别人的收藏的id", required = true)
	)
	@GetMapping("/createFrom")
	fun createFrom(@RequestParam id: Long, authentication: Authentication): Collect {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.createFrom(id, user)
	}
	
	@ApiOperation("删除自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'Collect','delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("修改自己的收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "collect", value = "修改后的收藏", required = true)
	)
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	fun modify(@PathVariable id: Long, @RequestBody @Valid collect: Collect, bindingResult: BindingResult): Collect {
		return service.modify(id, collect)
	}
	
	@ApiOperation("修改自己的收藏的分类。")
	@ApiImplicitParams(ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "category", value = "修改后的收藏的分类", required = true))
	@PutMapping("/{id}/category")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	fun modifyCategory(@PathVariable id: Long, @RequestBody category: CollectCategory): Collect {
		return service.modifyCategory(id, category)
	}
	
	@ApiOperation("修改自己的收藏的标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "tags", value = "修改后的收藏的标签", required = true)
	)
	@PutMapping("/{id}/tags")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	fun modifyTags(@PathVariable id: Long, @RequestBody tags: MutableSet<CollectTag>): Collect {
		return service.modifyTags(id, tags)
	}
	
	@ApiOperation("修改自己的收藏的类型。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "type", value = "修改后的收藏的类型", required = true)
	)
	@PutMapping("/{id}/type")
	@PreAuthorize("hasPermission(#id,'CollectCategory','write')")
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
	
	@ApiOperation("得到某一收藏。")
	@ApiImplicitParams(ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"))
	@GetMapping("/{id}")
	operator fun get(@PathVariable id: Long): Collect {
		return service.get(id)
	}
	
	@ApiOperation("分页得到某一收藏的所有点赞用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/praiseByUserPage")
	fun getPraiseByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return service.getPraiseByUserPage(id, pageable)
	}
	
	@ApiOperation("得到某一收藏的点赞用户数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/praiseByUserCount")
	fun getPraiseByUserCount(@PathVariable id: Long): Long {
		return service.getPraiseByUserCount(id)
	}
	
	@ApiOperation("分页得到某一收藏的所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return service.getCommentPage(id, pageable)
	}
	
	@ApiOperation("得到某一收藏的评论数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/commentCount")
	fun getCommentCount(@PathVariable id: Long): Long {
		return service.getCommentCount(id)
	}
	
	@ApiOperation("分页得到所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	fun findAll(@RequestParam pageable: Pageable): Page<Collect> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("分页查询某一用户的所有已删除/未删除收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "deleteStatus", value = "删除状态", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndDeleteStatus")
	fun findByUserAndDeleteStatus(@RequestParam userId: Long, @RequestParam deleteStatus: Boolean, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByUserAndDeleteStatus(userId, deleteStatus, pageable)
	}
	
	@ApiOperation("根据名字分页模糊查询某一用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndName")
	fun findByUserAndName(@RequestParam userId: Long, @RequestParam name: String, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByUserAndName(userId, name, pageable)
	}
	
	@ApiOperation("根据分类分页查询某一用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "categoryId", value = "分类的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndCategory")
	fun findByUserAndCategory(@RequestParam categoryId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByUserAndCategory(categoryId, pageable)
	}
	
	@ApiOperation("根据标签分页查询某一用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "tagId", value = "标签的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndCategory")
	fun findByUserAndTag(@RequestParam tagId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByUserAndTag(tagId, pageable)
	}
	
	@ApiOperation("根据类型分页查询某一用户的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "type", value = "收藏的类型", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndType")
	fun findByUserAndType(@RequestParam userId: Long, @RequestParam type: CollectType, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByUserAndType(userId, type, pageable)
	}
	
	@ApiOperation("根据名字分页全局查询所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByName")
	fun findByName(@RequestParam name: String, @RequestParam pageable: Pageable): Page<Collect> {
		return service.findByName(name, pageable)
	}
	
	@ApiOperation("从指定格式的文件导入收藏。例如：Xml、Json、Yaml。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "type", value = "数据类型", required = true),
		ApiImplicitParam(name = "file", value = "上传的文件", required = true)
	)
	@PostMapping("/import")
	fun importData(@RequestParam(defaultValue = "YAML") type: DataType, file: MultipartFile, authentication: Authentication) {
		//不检查文件格式是否正确，委托给前端
		val fileName = "dataSchema." + type.extension
		val filePath = Path.of("D:/CloudCollect/temp", fileName)
		file.transferTo(filePath)
		
		//读取写入过的文件内容，然后更新数据库
		val string = Files.readString(filePath)
		val user = (authentication.principal as JwtUserDetails).delegateUser
		ieService.importData(type, string, user)
	}
	
	@ApiOperation("导出收藏到指定格式的文件。例如：Xml、Json、Yaml。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "type", value = "数据类型", required = true)
	)
	@PostMapping("/export")
	fun exportData(@RequestParam(defaultValue = "YAML") type: DataType): ResponseEntity<ByteArray> {
		//不在本地缓存文件
		val fileName = "dataSchema." + type.extension
		val string = ieService.exportData(type)
		
		//设置响应头，并设置响应体为byte[]类型（也可以是InputStream、Resource等，间接得到byte[]）
		val headers = HttpHeaders()
		headers.contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build()
		return ResponseEntity(string.toByteArray(), headers, HttpStatus.OK)
	}
}
