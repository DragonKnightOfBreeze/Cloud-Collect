@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

/**收藏的分类的控制器。*/
@Api("收藏的分类")
@RestController
@RequestMapping("/collectCategory")
@CrossOrigin
class CollectCategoryController(
	private val categoryService: CollectCategoryService
) {
	@ApiOperation("创建自己的分类。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) category: CollectCategory, bindingResult: BindingResult, authentication: Authentication): CollectCategory {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return categoryService.create(category, user)
	}
	
	@ApiOperation("删除自己的分类。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun delete(@PathVariable id: Long) {
		categoryService.delete(id)
	}
	
	@ApiOperation("修改自己的分类。")
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) category: CollectCategory, bindingResult: BindingResult) {
		categoryService.modify(id, category)
	}
	
	@ApiOperation("根据id得到某一分类。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): CollectCategory {
		return categoryService.findById(id)
	}
	
	@ApiOperation("得到所有分类。")
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAll(pageable)
	}
	
	@ApiOperation("根据用户id查询所有分类。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有分类。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam userId: Long, @RequestParam name: String, @RequestParam pageable: Pageable): Page<CollectCategory> {
		return categoryService.findAllByNameContainsAndUserId(userId, name, pageable)
	}
	
	
	@ApiOperation("得到该分类的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PathVariable pageable: Pageable): Page<Collect> {
		return categoryService.getCollectPage(id, pageable)
	}
}
