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

/**收藏的标签的控制器。*/
@Api("收藏的标签")
@RestController
@RequestMapping("/collectTag")
@CrossOrigin
class CollectTagController(
	private val service: CollectTagService,
	private val collectService: CollectService
) {
	@ApiOperation("创建自己的标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "tag", value = "新的标签", required = true)
	)
	@PostMapping("/create")
	@PreAuthorize("hasPermission(0, 'CollectTag', 'create')")
	fun create(@RequestBody @Valid tag: CollectTag, bindingResult: BindingResult, authentication: Authentication): CollectTag {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.create(tag, user)
	}
	
	@ApiOperation("删除自己的标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'CollectTag', 'delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("修改自己的标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "tag", value = "修改后的标签", required = true)
	)
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'CollectTag', 'write')")
	fun modify(@PathVariable id: Long, @RequestBody @Valid tag: CollectTag, bindingResult: BindingResult): CollectTag {
		return service.modify(id, tag)
	}
	
	@ApiOperation("根据id得到某一标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): CollectTag {
		return service.findById(id)
	}
	
	@ApiOperation("分页得到某一标签的所有收藏。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id]/collectPage")
	fun getCollectPage(@PathVariable id: Long, @PathVariable pageable: Pageable): Page<Collect> {
		return collectService.findAllByTagIdAndDeletedFalse(id, pageable)
	}
	
	@ApiOperation("得到某一标签的收藏数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/collectCount")
	fun getCollectCount(@PathVariable id: Long): Long {
		return collectService.countByTagIdAndDeletedFalse(id)
	}
	
	@ApiOperation("分页得到所有标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<CollectTag> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("根据用户id分页查询所有标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<CollectTag> {
		return service.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("根据名字和用户id分页模糊查询所有标签。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "name", value = "名字", required = true),
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByNameContainsAndUserId")
	fun findAllByNameContainsAndUserId(@RequestParam name: String, @RequestParam userId: Long, @RequestParam pageable: Pageable): Page<CollectTag> {
		return service.findAllByNameContainsAndUserId(name, userId, pageable)
	}
}
