@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("收藏")
@RestController
@RequestMapping("/collect")
@CrossOrigin
class CollectController(
	private val collectService: CollectService,
	private val dataImportExportService: DataSerializeService
) {
	@ApiOperation("创建自己的收藏。")
	@PostMapping("/create")
	fun create(@RequestBody @Validated(Create::class) collect: Collect, bindingResult: BindingResult,
		authentication: Authentication) {
		collectService.create(collect, authentication.toUser())
	}
	
	@ApiOperation("从别人的收藏创建自己的收藏。")
	@PostMapping("/createFrom")
	fun createFrom(@RequestBody collect: Collect, authentication: Authentication) {
		collectService.createFrom(collect, authentication.toUser())
	}
	
	@ApiOperation("修改自己的收藏。")
	@PutMapping("/{id}")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) collect: Collect,
		bindingResult: BindingResult) {
		collectService.modify(id, collect)
	}
	
	@ApiOperation("点赞某一收藏。")
	@PutMapping("/{id}/praise")
	fun praise(@PathVariable id: Long, authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		collectService.praise(id, user)
	}
	
	@ApiOperation("删除自己的收藏。")
	@DeleteMapping("/{id}")
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
	
	@ApiOperation("根据分类名字全局查询所有收藏。")
	@GetMapping("/findAllByCategoryNameContains")
	fun findAllByCategoryNameContains(@RequestParam categoryName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryNameContains(categoryName, pageable)
	}
	
	@ApiOperation("根据标签名字全局查询所有收藏。")
	@GetMapping("/findAllByTagNameContains")
	fun findAllByTagNameContains(@RequestParam tagName: String, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagNameContains(tagName, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@ApiOperation("根据分类名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByCategoryNameContainsAndUserId")
	fun findAllByCategoryNameContainsAndUserId(@RequestParam categoryName: String, @RequestParam userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryNameContainsAndUserId(categoryName, userId, pageable)
	}
	
	@ApiOperation("根据标签名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByTagNameContainsAndUserId")
	fun findAllByTagNameContainsAndUserId(@RequestParam tagName: String, @RequestParam userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagNameContainsAndUserId(tagName, userId, pageable)
	}
	
	@ApiOperation("根据分类id查询所有收藏。")
	@GetMapping("/findByUserAndCategory")
	fun findAllByCategoryId(@RequestParam categoryId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryId(categoryId, pageable)
	}
	
	@ApiOperation("根据标签id查询所有收藏。")
	@GetMapping("/findAllByTagId")
	fun findAllByTagId(@RequestParam tagId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagId(tagId, pageable)
	}
	
	@ApiOperation("根据类型和用户id查询所有收藏。")
	@GetMapping("/findAllByTypeAndUserId")
	fun findAllByTypeAndUserId(@RequestParam type: CollectType, @RequestParam userId: Long,
		pageable: Pageable): Page<Collect> {
		return collectService.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@ApiOperation("根据用户id和收藏状态查询所有收藏。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Collect> {
		return collectService.findAllByUserId(userId, pageable)
	}
	
	
	@ApiOperation("判断指定用户是否已点赞指定收藏。")
	@GetMapping("/{id}/isPraised")
	fun isPraised(@PathVariable id: Long, authentication: Authentication): Boolean {
		return collectService.isPraised(id, authentication.toUser())
	}
	
	@ApiOperation("得到该收藏的所有点赞用户。")
	@GetMapping("/{id}/praiseByUserPage")
	fun getPraiseByUserPage(@PathVariable id: Long, pageable: Pageable): Page<User> {
		return collectService.getPraiseByUserPage(id, pageable)
	}
	
	@ApiOperation("得到该收藏的所有评论。")
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, pageable: Pageable): Page<Comment> {
		return collectService.getCommentPage(id, pageable)
	}
}
