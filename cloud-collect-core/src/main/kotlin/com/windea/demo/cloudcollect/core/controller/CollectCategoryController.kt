@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/** 收藏的分类的控制器。*/
@Api("收藏的分类")
@RestController
@RequestMapping("/collectCategory")
@CrossOrigin
class CollectCategoryController(
	private val service: CollectCategoryService
) {
	@ApiOperation("创建自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "category", value = "新的分类", required = true)
	)
	@PostMapping("/create")
	fun create(@RequestBody @Valid category: CollectCategory, bindingResult: BindingResult, authentication: Authentication): CollectCategory {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.create(category, user)
	}
	
	@ApiOperation("删除自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'CollectCategory','delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("修改自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "category", value = "修改后的分类", required = true)
	)
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'CollectCategory','write')")
	fun modify(@PathVariable id: Long, @RequestBody @Valid category: CollectCategory, bindingResult: BindingResult): CollectCategory {
		return service.modify(id, category)
	}
	
	@ApiOperation("得到某一分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	operator fun get(@PathVariable id: Long): CollectCategory {
		return service.get(id)
	}
	
	@ApiOperation("分页得到某一分类的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id]/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PathVariable pageable: Pageable): Page<Collect> {
		return service.getCollectPage(id, pageable)
	}
	
	@ApiOperation("得到某一分类的收藏数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return service.getCollectCount(id)
	}
	
	@ApiOperation("分页得到所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	fun findAll(@RequestParam pageable: Pageable): Page<CollectCategory> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("分页得到某一用户的所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUser")
	fun findByUser(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return service.findByUser(userId, pageable)
	}
	
	@ApiOperation("根据名字分页模糊查询某一用户的所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUserAndName")
	fun findByUserAndName(@RequestParam userId: Long, @RequestParam name: String, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return service.findByUserAndName(userId, name, pageable)
	}
}
