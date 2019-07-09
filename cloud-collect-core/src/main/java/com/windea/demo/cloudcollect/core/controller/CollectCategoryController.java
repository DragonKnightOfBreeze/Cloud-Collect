package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 收藏的分类的控制器。
 */
@Api("收藏的分类")
@RestController
@RequestMapping("/collectCategory")
@CrossOrigin
public class CollectCategoryController {
	private final CollectCategoryService service;

	public CollectCategoryController(CollectCategoryService service) {
		this.service = service;
	}


	@ApiOperation("创建自己的分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "新的分类", required = true)
	})
	@PostMapping("/create")
	public CollectCategory create(@RequestBody @Valid CollectCategory category, BindingResult bindingResult,
		Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return service.create(category, user);
	}

	@ApiOperation("删除自己的分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'CollectCategory','delete')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@ApiOperation("修改自己的分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "category", value = "修改后的分类", required = true)
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'CollectCategory','write')")
	public CollectCategory modify(@PathVariable Long id, @RequestBody @Valid CollectCategory category,
		BindingResult bindingResult) {
		return service.modify(id, category);
	}

	@ApiOperation("得到某一分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public CollectCategory get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到某一分类的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id]/collectPage")
	public Page<Collect> getCollectPage(@PathVariable Long id, @PathVariable Pageable pageable) {
		return service.getCollectPage(id, pageable);
	}

	@ApiOperation("得到某一分类的收藏数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/collectCount")
	public Long getCollectCount(@PathVariable Long id) {
		return service.getCollectCount(id);
	}

	@ApiOperation("分页得到所有分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<CollectCategory> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("分页得到某一用户的所有分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUser")
	public Page<CollectCategory> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@ApiOperation("根据名字分页模糊查询某一用户的所有分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "name", value = "名字", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndName")
	public Page<CollectCategory> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}
}
