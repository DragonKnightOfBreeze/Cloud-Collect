package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 收藏的标签的控制器。
 */
@Api("收藏的标签")
@RestController
@RequestMapping("/collectTag")
@CrossOrigin
public class CollectTagController {
	private final CollectTagService service;

	public CollectTagController(CollectTagService service) {
		this.service = service;
	}

	@ApiOperation("创建自己的标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tag", value = "新的标签", required = true)
	})
	@PostMapping("/create")
	public void create(@RequestBody @Valid CollectTag tag, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(tag, user);
	}

	@ApiOperation("删除自己的标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'com.windea.demo.cloudcollect.core.domain.entity.CollectTag','delete')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@ApiOperation("修改自己的标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "tag", value = "修改后的标签", required = true)
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'com.windea.demo.cloudcollect.core.domain.entity.CollectTag','write')")
	public void modify(@PathVariable Long id, @RequestBody @Valid CollectTag tag, BindingResult bindingResult) {
		service.modify(id, tag);
	}

	@ApiOperation("得到某一标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public CollectTag get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到某一标签的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id]/collectPage")
	public Page<Collect> getCollectPage(@PathVariable Long id, @PathVariable Pageable pageable) {
		return service.getCollectPage(id, pageable);
	}

	@ApiOperation("得到某一标签的收藏数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/collectCount")
	public Long getCollectCount(@PathVariable Long id) {
		return service.getCollectCount(id);
	}

	@ApiOperation("分页得到所有标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<CollectTag> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("分页得到某一用户的所有标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUser")
	public Page<CollectTag> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@ApiOperation("根据名字分页模糊查询某一用户的所有标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "name", value = "名字", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndName")
	public Page<CollectTag> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}
}
