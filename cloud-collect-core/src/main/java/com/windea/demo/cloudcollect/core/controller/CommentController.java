package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 评论的控制器。
 */
@Api("评论")
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
	private final CommentService service;

	public CommentController(CommentService service) {
		this.service = service;
	}


	@ApiOperation("创建自己的评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		@ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	})
	@PostMapping("/create")
	public void create(@RequestParam Long collectId,
		@RequestBody @Valid Comment comment, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(collectId, comment, user);
	}

	@ApiOperation("创建自己的评论，回复某一评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "collectId", value = "评论的收藏的id", required = true),
		@ApiImplicitParam(name = "replyToCommentId", value = "回复的评论的id", required = true),
		@ApiImplicitParam(name = "comment", value = "新的评论", required = true)
	})
	@PostMapping("/reply")
	public void reply(@RequestParam Long collectId, @RequestParam Long replyToCommentId,
		@RequestBody @Valid Comment comment, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.reply(collectId, replyToCommentId, comment, user);
	}

	@ApiOperation("删除自己的评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@ApiOperation("得到某一评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public Comment get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到回复某一评论的所有评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/replyByCommentPage")
	public Page<Comment> getReplyByCommentPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getReplyByCommentPage(id, pageable);
	}

	@ApiOperation("得到回复某一评论的评论数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/replyByCommentCount")
	public Long getReplyByCommentCount(@PathVariable Long id) {
		return service.getReplyByCommentCount(id);
	}

	@ApiOperation("分页得到所有评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	public Page<Comment> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("分页查询某一收藏的所有评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "collectId", value = "收藏的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByCollect")
	public Page<Comment> findByCollect(@RequestParam Long collectId, @RequestParam Pageable pageable) {
		return service.findByCollect(collectId, pageable);
	}
}
