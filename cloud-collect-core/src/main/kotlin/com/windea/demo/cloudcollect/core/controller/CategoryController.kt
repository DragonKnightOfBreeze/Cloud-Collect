@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("收藏的分类")
@RestController
@RequestMapping("/category")
@CrossOrigin
class CategoryController(
	private val categoryService: CategoryService
) {
	@ApiOperation("创建自己的分类。")
	@PostMapping("/create")
	fun create(@RequestBody @Validated(Create::class) category: Category, bindingResult: BindingResult) {
		categoryService.create(category)
	}
	
	@ApiOperation("修改自己的分类。")
	@PutMapping("/{id}")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) category: Category,
		bindingResult: BindingResult) {
		categoryService.modify(id, category)
	}
	
	@ApiOperation("删除自己的分类。")
	@DeleteMapping("/{id}")
	fun deleteById(@PathVariable id: Long) {
		categoryService.deleteById(id)
	}
	
	@ApiOperation("根据id得到某一分类。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Category {
		return categoryService.findById(id)
	}
	
	@ApiOperation("得到所有分类。")
	@GetMapping("/findAll")
	fun findAll(pageable: Pageable): Page<Category> {
		return categoryService.findAll(pageable)
	}
	
	@ApiOperation("根据名字模糊查询所有分类。")
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, pageable: Pageable): Page<Category> {
		return categoryService.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据用户id查询所有分类。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Category> {
		return categoryService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有分类。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam userId: Long, @RequestParam name: String,
		pageable: Pageable): Page<Category> {
		return categoryService.findAllByNameContainsAndUserId(userId, name, pageable)
	}
	
	
	@ApiOperation("得到该分类的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, pageable: Pageable): Page<Collect> {
		return categoryService.getCollectPage(id, pageable)
	}
}
