package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.exception.ValidationException;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 收藏的标签的控制器。
 */
@RestController
@RequestMapping("/collectTag")
@CrossOrigin
public class CollectTagController {
	private final CollectTagService service;

	public CollectTagController(CollectTagService service) {
		this.service = service;
	}


	@PostMapping("/create")
	public void create(@RequestBody @Valid CollectTag tag, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(tag, user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable Long id, @RequestBody CollectTag tag, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
		service.modify(id, tag);
	}

	@GetMapping("/{id}")
	public CollectTag get(@PathVariable Long id) {
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
	public Page<CollectTag> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/findByUser")
	public Page<CollectTag> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@GetMapping("/findByUserAndName")
	public Page<CollectTag> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}
}
