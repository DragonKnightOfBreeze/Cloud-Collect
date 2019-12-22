@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.Tag
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("收藏的标签")
@RestController
@RequestMapping("/tag")
@CrossOrigin
class TagController(
	private val tagService: TagService
) {
	@ApiOperation("创建自己的标签。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) tag: Tag, bindingResult: BindingResult) {
		tagService.create(tag)
	}
	
	@ApiOperation("修改自己的标签。")
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) tag: Tag, bindingResult: BindingResult) {
		tagService.modify(id, tag)
	}
	
	@ApiOperation("删除自己的标签。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		tagService.deleteById(id)
	}
	
	@ApiOperation("根据id得到某一标签。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Tag {
		return tagService.findById(id)
	}
	
	@ApiOperation("得到所有标签。")
	@GetMapping("/findAll")
	fun findAll(pageable: Pageable): Page<Tag> {
		return tagService.findAll(pageable)
	}
	
	@ApiOperation("根据名字模糊查询所有分类。")
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, pageable: Pageable): Page<Tag> {
		return tagService.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据用户id查询所有标签。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Tag> {
		return tagService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有标签。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long, pageable: Pageable): Page<Tag> {
		return tagService.findAllByNameContainsAndUserId(name, userId, pageable)
	}
}
