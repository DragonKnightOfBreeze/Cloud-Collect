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

/**评论的控制器。*/
@Api("评论")
@RestController
@RequestMapping("/comment")
@CrossOrigin
class CommentController(
	private val commentService: CommentService
) {
	@ApiOperation("创建自己的评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	)
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestParam collectId: Long,
		@RequestBody @Valid comment: Comment, bindingResult: BindingResult, authentication: Authentication): Comment {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return commentService.create(collectId, comment, user)
	}
	
	@ApiOperation("创建自己的评论，回复某一评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		ApiImplicitParam(name = "replyToCommentId", value = "回复的评论的id", required = true),
		ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	)
	@PostMapping("/reply")
	@PreAuthorize("isAuthenticated()")
	fun reply(@RequestParam collectId: Long, @RequestParam replyToCommentId: Long,
		@RequestBody @Valid comment: Comment, bindingResult: BindingResult, authentication: Authentication): Comment {
		val user = (authentication.principal as JwtUserDetails).delegateUser
		return commentService.reply(collectId, replyToCommentId, comment, user)
	}
	
	@ApiOperation("删除自己的评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Comment', 'delete')")
	fun delete(@PathVariable id: Long) {
		commentService.delete(id)
	}
	
	@ApiOperation("根据id得到某一评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Comment {
		return commentService.findById(id)
	}
	
	@ApiOperation("得到所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Comment> {
		return commentService.findAll(pageable)
	}
	
	@ApiOperation("根据收藏id查询所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "collectId", value = "收藏的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByCollectId")
	fun findAllByCollectId(@RequestParam collectId: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return commentService.findAllByCollectId(collectId, pageable)
	}
	
	
	@ApiOperation("得到回复某一评论的所有评论。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/{id}/replyByCommentPage")
	fun getReplyByCommentPage(@PathVariable id: Long, @RequestParam pageable: Pageable): Page<Comment> {
		return commentService.getReplyByCommentPage(id, pageable)
	}
	
	@ApiOperation("得到回复某一评论的评论数量。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}/replyByCommentCount")
	fun getReplyByCommentCount(@PathVariable id: Long): Long {
		return commentService.getReplyByCommentCount(id)
	}
}
