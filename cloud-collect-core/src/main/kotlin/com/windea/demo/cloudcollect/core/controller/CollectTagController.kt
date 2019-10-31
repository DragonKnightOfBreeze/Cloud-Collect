@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("收藏的标签")
@RestController
@RequestMapping("/collectTag")
@CrossOrigin
class CollectTagController(
	private val tagService: CollectTagService
) {
	@ApiOperation("创建自己的标签。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) tag: CollectTag, bindingResult: BindingResult,
		authentication: Authentication) {
		tagService.create(tag, authentication.toUser())
	}
	
	@ApiOperation("修改自己的标签。")
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) tag: CollectTag, bindingResult: BindingResult) {
		tagService.modify(tag)
	}
	
	@ApiOperation("删除自己的标签。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		tagService.deleteById(id)
	}
	
	@ApiOperation("根据id得到某一标签。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): CollectTag {
		return tagService.findById(id)
	}
	
	@ApiOperation("得到所有标签。")
	@GetMapping("/findAll")
	fun findAll(pageable: Pageable): Page<CollectTag> {
		return tagService.findAll(pageable)
	}
	
	@ApiOperation("根据名字模糊查询所有分类。")
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, pageable: Pageable): Page<CollectTag> {
		return tagService.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据用户id查询所有标签。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<CollectTag> {
		return tagService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有标签。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long,
		pageable: Pageable): Page<CollectTag> {
		return tagService.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	
	@ApiOperation("得到某一标签的所有收藏。")
	@GetMapping("/{id}/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PathVariable pageable: Pageable): Page<Collect> {
		return tagService.getCollectPage(id, pageable)
	}
}
