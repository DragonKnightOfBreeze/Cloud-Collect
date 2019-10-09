@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

/**收藏的控制器。*/
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
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) collect: Collect, bindingResult: BindingResult, authentication: Authentication): Collect {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return collectService.create(collect, user)
	}
	
	@ApiOperation("从别人的收藏创建自己的收藏。")
	@PostMapping("/createFrom")
	@PreAuthorize("isAuthenticated()")
	fun createFrom(@RequestParam id: Long, authentication: Authentication): Collect {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return collectService.createFrom(id, user)
	}
	
	@ApiOperation("删除自己的收藏。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun delete(@PathVariable id: Long) {
		collectService.delete(id)
	}
	
	@ApiOperation("修改自己的收藏。")
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun modify(@PathVariable id: Long, @RequestBody @Validated(Modify::class) collect: Collect, bindingResult: BindingResult) {
		collectService.modify(id, collect)
	}
	
	@ApiOperation("修改自己的收藏的分类。")
	@PutMapping("/{id}/category")
	@PreAuthorize("isAuthenticated()")
	fun modifyCategory(@PathVariable id: Long, @RequestBody category: CollectCategory) {
		collectService.modifyCategory(id, category)
	}
	
	@ApiOperation("修改自己的收藏的标签。")
	@PutMapping("/{id}/tags")
	@PreAuthorize("isAuthenticated()")
	fun modifyTags(@PathVariable id: Long, @RequestBody tags: MutableSet<CollectTag>) {
		collectService.modifyTags(id, tags)
	}
	
	@ApiOperation("修改自己的收藏的类型。")
	@PutMapping("/{id}/type")
	@PreAuthorize("isAuthenticated()")
	fun modifyType(@PathVariable id: Long, @RequestBody type: CollectType) {
		collectService.modifyType(id, type)
	}
	
	@ApiOperation("点赞某一收藏。")
	@PutMapping("/{id}/praise")
	fun praise(@PathVariable id: Long, authentication: Authentication) {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		collectService.praise(id, user)
	}
	
	@ApiOperation("根据id得到某一收藏。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Collect {
		return collectService.findById(id)
	}
	
	@ApiOperation("得到所有收藏。")
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAll(pageable)
	}
	
	@ApiOperation("根据名字全局查询所有收藏。")
	@GetMapping("/findAllByNameContains")
	fun findAllByNameContains(@RequestParam name: String, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContains(name, pageable)
	}
	
	@ApiOperation("根据名字和用户id模糊查询所有收藏。")
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByNameContainsAndUserId(name, userId, pageable)
	}
	
	@ApiOperation("根据分类id查询所有收藏。")
	@GetMapping("/findByUserAndCategory")
	fun findAllByCategoryId(@RequestParam categoryId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByCategoryId(categoryId, pageable)
	}
	
	@ApiOperation("根据标签id查询所有收藏。")
	@GetMapping("/findAllByTagId")
	fun findAllByTagId(@RequestParam tagId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagId(tagId, pageable)
	}
	
	@ApiOperation("根据类型和用户id查询所有收藏。")
	@GetMapping("/findAllByTypeAndUserId")
	fun findAllByTypeAndUserId(@RequestParam type: CollectType, @RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByTypeAndUserId(type, userId, pageable)
	}
	
	@ApiOperation("根据用户id和收藏状态查询所有收藏。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Collect> {
		return collectService.findAllByUserId(userId, pageable)
	}
	
	
	@ApiOperation("得到该收藏的所有点赞用户。")
	@GetMapping("/{id}/praiseByUserPage")
	fun getPraiseByUserPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<User> {
		return collectService.getPraiseByUserPage(id, pageable)
	}
	
	@ApiOperation("得到该收藏的所有评论。")
	@GetMapping("/{id}/commentPage")
	fun getCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return collectService.getCommentPage(id, pageable)
	}
}
