package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CollectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

/**
 * 收藏的控制器。
 */
@RequestMapping("/collect")
@RestController
@CrossOrigin
public class CollectController {
	private final CollectService service;

	public CollectController(CollectService service) {
		this.service = service;
	}


	@PostMapping("/create")
	public void create(@RequestBody @Valid Collect collect, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(collect, user);
	}

	@PostMapping("/createFrom")
	public void createFrom(@RequestBody @Valid Collect collect, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.createFrom(collect, user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable Long id, @RequestBody @Valid Collect collect, BindingResult bindingResult) {
		service.modify(id, collect);
	}

	@PutMapping("/{id}/category")
	public void modifyCategory(@PathVariable Long id, @RequestBody CollectCategory category) {
		service.modifyCategory(id, category);
	}

	@PutMapping("/{id}/tags")
	public void modifyTags(@PathVariable Long id, @RequestBody Set<CollectTag> tags) {
		service.modifyTags(id, tags);
	}

	@PutMapping("/{id}/praise")
	public void praise(@PathVariable Long id, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.praise(id, user);
	}

	@GetMapping("/{id}")
	public Collect get(@PathVariable Long id) {
		return service.get(id);
	}

	@GetMapping("/{id}/praiseByUserPage")
	public Page<User> getPraiseByUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getPraiseByUserPage(id, pageable);
	}

	@GetMapping("/{id}/praiseByUserCount")
	public Long getPraiseByUserCount(@PathVariable Long id) {
		return service.getPraiseByUserCount(id);
	}

	@GetMapping("/{id}/commentPage")
	public Page<Comment> getCommentPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCommentPage(id, pageable);
	}

	@GetMapping("/{id}/commentCount")
	public Long getCommentCount(@PathVariable Long id) {
		return service.getCommentCount(id);
	}

	@GetMapping("/findAll")
	public Page<Collect> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/findByUserAndDeleteStatus")
	public Page<Collect> findByUserAndDeleteStatus(@RequestParam Long userId, @RequestParam Boolean deleteStatus,
		@RequestParam Pageable pageable) {
		return service.findByUserAndDeleteStatus(userId, deleteStatus, pageable);
	}

	@GetMapping("/findByUserAndName")
	public Page<Collect> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}

	@GetMapping("/findByUserAndCategory")
	public Page<Collect> findByUserAndCategory(@RequestParam Long categoryId, @RequestParam Pageable pageable) {
		return service.findByUserAndCategory(categoryId, pageable);
	}

	@GetMapping("/findByUserAndType")
	public Page<Collect> findByUserAndType(@RequestParam Long userId, @RequestParam CollectType type,
		@RequestParam Pageable pageable) {
		return service.findByUserAndType(userId, type, pageable);
	}

	@GetMapping("/findByName")
	public Page<Collect> findByName(@RequestParam String name, @RequestParam Pageable pageable) {
		return service.findByName(name, pageable);
	}
}
