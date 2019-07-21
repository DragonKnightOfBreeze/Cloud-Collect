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

/**评论的控制器。*/
@Api("评论")
@RestController
@RequestMapping("/comment")
@CrossOrigin
class CommentController(
	private val service: CommentService
) {
	@ApiOperation("创建自己的评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	)
	@PostMapping("/create")
	@PreAuthorize("hasPermission(0, 'Comment', 'create')")
	fun create(@RequestParam collectId: Long,
		@RequestBody @Valid comment: Comment, bindingResult: BindingResult, authentication: Authentication): Comment {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.create(collectId, comment, user)
	}
	
	@ApiOperation("创建自己的评论，回复某一评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		ApiImplicitParam(name = "replyToCommentId", value = "回复的评论的id", required = true),
		ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	)
	@PostMapping("/reply")
	@PreAuthorize("hasPermission(0, 'Comment', 'create')")
	fun reply(@RequestParam collectId: Long, @RequestParam replyToCommentId: Long,
		@RequestBody @Valid comment: Comment, bindingResult: BindingResult, authentication: Authentication): Comment {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return service.reply(collectId, replyToCommentId, comment, user)
	}
	
	@ApiOperation("删除自己的评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Comment', 'delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("根据id得到某一评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Comment {
		return service.findById(id)
	}
	
	@ApiOperation("分页得到回复某一评论的所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/replyByCommentPage")
	fun getReplyByCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return service.findAllByReplyToCommentId(id, pageable)
	}
	
	@ApiOperation("得到回复某一评论的评论数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/replyByCommentCount")
	fun getReplyByCommentCount(@PathVariable id: Long): Long {
		return service.countByReplyToCommentId(id)
	}
	
	@ApiOperation("分页得到所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Comment> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("根据收藏id分页查询所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "收藏的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByCollectId")
	fun findAllByCollectId(@RequestParam collectId: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return service.findAllByCollectId(collectId, pageable)
	}
}
