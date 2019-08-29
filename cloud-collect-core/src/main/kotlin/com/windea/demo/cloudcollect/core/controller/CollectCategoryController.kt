@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/**收藏的分类的控制器。*/
@Api("收藏的分类")
@RestController
@RequestMapping("/collectCategory")
@CrossOrigin
class CollectCategoryController(
	private val categoryService: CollectCategoryService
) {
	@ApiOperation("创建自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "category", value = "新的分类", required = true)
	)
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Valid category: CollectCategory, bindingResult: BindingResult, authentication: Authentication): CollectCategory {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return categoryService.create(category, user)
	}
	
	@ApiOperation("删除自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'CollectCategory', 'delete')")
	fun delete(@PathVariable id: Long) {
		categoryService.delete(id)
	}
	
	@ApiOperation("修改自己的分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "category", value = "修改后的分类", required = true)
	)
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'CollectCategory', 'write')")
	fun modify(@PathVariable id: Long, @RequestBody @Valid category: CollectCategory, bindingResult: BindingResult): CollectCategory {
		return categoryService.modify(id, category)
	}
	
	@ApiOperation("根据id得到某一分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): CollectCategory {
		return categoryService.findById(id)
	}
	
	@ApiOperation("得到所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAll(pageable)
	}
	
	@ApiOperation("根据用户id查询所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有分类。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam userId: Long, @RequestParam name: String, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAllByNameContainsAndUserId(userId, name, pageable)
	}
	
	
	@ApiOperation("得到该分类的收藏数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return categoryService.getCollectCount(id)
	}
	
	@ApiOperation("得到该分类的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PathVariable pageable: Pageable): Page<Collect> {
		return categoryService.getCollectPage(id, pageable)
	}
}
