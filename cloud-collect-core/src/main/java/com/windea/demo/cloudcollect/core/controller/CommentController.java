package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.Comment;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.exception.ValidationException;
import com.windea.demo.cloudcollect.core.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 评论的控制器。
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
	private final CommentService service;

	public CommentController(CommentService service) {
		this.service = service;
	}


	@PostMapping("/create")
	public void create(@RequestBody @Valid Comment comment, BindingResult bindingResult,
		Collect collect, Principal principal) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(comment, collect, user);
	}

	@PostMapping("/reply")
	public void reply(@RequestBody @Valid Comment comment, BindingResult bindingResult,
		Collect collect, Comment replyToComment, Principal principal) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.reply(comment, collect, replyToComment, user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@GetMapping("/{id}")
	public Comment get(@PathVariable Long id) {
		return service.get(id);
	}

	@GetMapping("/{id}/replyByCommentPage")
	public Page<Comment> getReplyByCommentPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getReplyByCommentPage(id, pageable);
	}

	@GetMapping("/{id}/replyByCommentCount")
	public Long getReplyByCommentCount(@PathVariable Long id) {
		return service.getReplyByCommentCount(id);
	}

	@GetMapping("/queryByCollect")
	public Page<Comment> queryByCollect(@RequestParam Long collectId, @RequestParam Pageable pageable) {
		return service.queryByCollect(collectId, pageable);
	}
}
