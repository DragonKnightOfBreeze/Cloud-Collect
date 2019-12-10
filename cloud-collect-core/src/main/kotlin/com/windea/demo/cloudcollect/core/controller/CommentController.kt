@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@Api("评论")
@RestController
@RequestMapping("/comment")
@CrossOrigin
class CommentController(
	private val commentService: CommentService
) {
	@ApiOperation("创建自己的评论。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody @Validated(Create::class) comment: Comment, bindingResult: BindingResult) {
		commentService.create(comment)
	}
	
	@ApiOperation("回复某一评论。")
	@PostMapping("/reply")
	@PreAuthorize("isAuthenticated()")
	fun reply(@RequestBody @Validated(Create::class) comment: Comment, bindingResult: BindingResult) {
		commentService.reply(comment)
	}
	
	@ApiOperation("删除自己的评论。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		commentService.deleteById(id)
	}
	
	@ApiOperation("根据id得到某一评论。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Comment {
		return commentService.findById(id)
	}
	
	@ApiOperation("得到所有评论。")
	@GetMapping("/findAll")
	fun findAll(pageable: Pageable): Page<Comment> {
		return commentService.findAll(pageable)
	}
	
	@ApiOperation("根据收藏id查询所有评论。")
	@GetMapping("/findAllByCollectId")
	fun findAllByCollectId(@RequestParam collectId: Long, pageable: Pageable): Page<Comment> {
		return commentService.findAllByCollectId(collectId, pageable)
	}
	
	@ApiOperation("根据发起用户id查询所有评论。")
	@GetMapping("/findAllBySponsorByUserId")
	fun findAllBySponsorByUserId(@RequestParam sponsorByUserId: Long, pageable: Pageable): Page<Comment> {
		return commentService.findAllBySponsorByUserId(sponsorByUserId, pageable)
	}
	
	@ApiOperation("根据回复评论id查询所有评论。")
	@GetMapping("/findAllByReplyToCommentId")
	fun findAllByReplyToCommentId(@RequestParam replyToCommentId: Long, pageable: Pageable): Page<Comment> {
		return commentService.findAllByReplyToCommentId(replyToCommentId, pageable)
	}
}
