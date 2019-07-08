package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 收藏的分类的控制器。
 */
@RestController
@RequestMapping("/collectCategory")
@CrossOrigin
public class CollectCategoryController {
	private final CollectCategoryService service;

	public CollectCategoryController(CollectCategoryService service) {
		this.service = service;
	}


	@PostMapping("/create")
	public void create(@RequestBody @Valid CollectCategory category, BindingResult bindingResult,
		Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(category, user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable Long id, @RequestBody CollectCategory category, BindingResult bindingResult) {
		service.modify(id, category);
	}

	@GetMapping("/{id}")
	public CollectCategory get(@PathVariable Long id) {
		return service.get(id);
	}

	@GetMapping("/{id]/collectPage")
	public Page<Collect> getCollectPage(@PathVariable Long id, @PathVariable Pageable pageable) {
		return service.getCollectPage(id, pageable);
	}

	@GetMapping("/{id}/collectCount")
	public Long getCollectCount(@PathVariable Long id) {
		return service.getCollectCount(id);
	}

	@GetMapping("/findAll")
	public Page<CollectCategory> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/findByUser")
	public Page<CollectCategory> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@GetMapping("/findByUserAndName")
	public Page<CollectCategory> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}
}
