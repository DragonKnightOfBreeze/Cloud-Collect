@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("收藏")
@RestController
@RequestMapping("/collect")
@CrossOrigin
class CollectController(
	private val collectService: CollectService
) {
	@ApiOperation("创建自己的收藏。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) collect: Collect, bindingResult: BindingResult) {
		collectService.create(collect)
	}
	
	@ApiOperation("从别人的收藏创建自己的收藏。")
	@PostMapping("/fork")
	@PreAuthorize("isAuthenticated()")
	fun fork(@RequestBody collect: Collect) {
		collectService.fork(collect)
	}
	
	@ApiOperation("修改自己的收藏。")
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) collect: Collect, bindingResult: BindingResult) {
		collectService.modify(id, collect)
	}
	
	@ApiOperation("点赞某一收藏。")
	@PutMapping("/{id}/praise")
	@PreAuthorize("isAuthenticated()")
	fun praise(@PathVariable id: Long) {
		collectService.praise(id)
	}
	
	@ApiOperation("取消点赞某一收藏。")
	@PutMapping("/{id}/unpraise")
	@PreAuthorize("isAuthenticated()")
	fun unpraise(@PathVariable id: Long) {
		collectService.unpraise(id)
	}
	
	@ApiOperation("删除自己的收藏。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		collectService.deleteById(id)
	}
	
	@ApiOperation("根据id得到某一收藏。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Collect {
		return collectService.findById(id)
	}
	
	@ApiOperation("得到所有收藏。")
	@GetMapping("/findAll")
	fun findAll(pageable: Pageable): Page<Collect> {
		return collectService.findAll(pageable)
	}
	
	@ApiOperation("根据名字全局查询所有收藏。")
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据分类id查询所有收藏。")
	@GetMapping("/findAllByCategoryId")
	fun findAllByCategoryId(@RequestParam categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryId(categoryId, pageable)
	}
	
	@ApiOperation("根据分类名字全局查询所有收藏。")
	@GetMapping("/findAllByCategoryName")
	fun findAllByCategoryName(@RequestParam categoryName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryName(categoryName, pageable)
	}
	
	@ApiOperation("根据分类名字全局模糊查询所有收藏。")
	@GetMapping("/findAllByCategoryNameContains")
	fun findAllByCategoryNameContains(@RequestParam categoryName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryNameContains(categoryName, pageable)
	}
	
	@ApiOperation("根据标签id查询所有收藏。")
	@GetMapping("/findAllByTagId")
	fun findAllByTagId(@RequestParam tagId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagId(tagId, pageable)
	}
	
	@ApiOperation("根据标签名字全局查询所有收藏。")
	@GetMapping("/findAllByTagName")
	fun findAllByTagName(@RequestParam tagName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagName(tagName, pageable)
	}
	
	@ApiOperation("根据标签名字全局模糊查询所有收藏。")
	@GetMapping("/findAllByTagNameContains")
	fun findAllByTagNameContains(@RequestParam tagName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagNameContains(tagName, pageable)
	}
	
	@ApiOperation("根据用户id和收藏状态查询所有收藏。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@ApiOperation("根据分类名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByCategoryNameContainsAndUserId")
	fun findAllByCategoryNameContainsAndUserId(@RequestParam categoryName: String, @RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryNameContainsAndUserId(categoryName, userId, pageable)
	}
	
	@ApiOperation("根据标签名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByTagNameContainsAndUserId")
	fun findAllByTagNameContainsAndUserId(@RequestParam tagName: String, @RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagNameContainsAndUserId(tagName, userId, pageable)
	}
	
	@ApiOperation("根据类型和用户id查询所有收藏。")
	@GetMapping("/findAllByTypeAndUserId")
	fun findAllByTypeAndUserId(@RequestParam type: CollectType, @RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@ApiOperation("根据点赞用户id查询所有收藏。")
	@GetMapping("/findAllByPraiseByUserId")
	fun findAllByPraiseByUserId(@RequestParam praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByPraiseByUserId(praiseByUserId, pageable)
	}
	
	@ApiOperation("根据名字和点赞用户id模糊查询所有收藏。")
	@GetMapping("/findAllByNameContainsAndPraiseByUserId")
	fun findAllByNameContainsAndPraiseByUserId(@RequestParam name: String, @RequestParam praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContainsAndPraiseByUserId(name, praiseByUserId, pageable)
	}
	
	@ApiOperation("根据分类名字和点赞用户id模糊查询所有收藏。")
	@GetMapping("/findAllByCategoryNameContainsAndPraiseByUserId")
	fun findAllByCategoryNameContainsAndPraiseByUserId(@RequestParam categoryName: String, @RequestParam praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryNameContainsAndPraiseByUserId(categoryName, praiseByUserId, pageable)
	}
	
	@ApiOperation("根据类型和点赞用户id查询所有收藏。")
	@GetMapping("/findAllByTagNameContainsAndPraiseByUserId")
	fun findAllByTagNameContainsAndPraiseByUserId(@RequestParam tagName: String, @RequestParam praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagNameContainsAndPraiseByUserId(tagName, praiseByUserId, pageable)
	}
	
	@ApiOperation("根据标签名字和点赞用户id模糊查询所有收藏。")
	@GetMapping("/findAllByTypeAndPraiseByUserId")
	fun findAllByTypeAndPraiseByUserId(@RequestParam type: CollectType, @RequestParam praiseByUserId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTypeAndPraiseByUserId(type, praiseByUserId, pageable)
	}
}
