package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.NoticeService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 通知的控制器。
 */
@Api("通知")
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
	private final NoticeService service;

	public NoticeController(NoticeService service) {
		this.service = service;
	}


	@ApiOperation("创建通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "notice", value = "新的通知", required = true)
	})
	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void create(@RequestBody @Valid Notice notice, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		service.create(notice);
	}

	@ApiOperation("删除自己的通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'Notice','delete')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@ApiOperation("阅读自己的通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@PutMapping("/{id}/read")
	public void read(@PathVariable Long id) {
		service.read(id);
	}

	@ApiOperation("得到某一通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public Notice get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到所有通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<Notice> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("分页查询某一用户的所有通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUser")
	public Page<Notice> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@ApiOperation("分页查询某一用户的所有已读/未读通知。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "readStatus", value = "阅读状态", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndReadStatus")
	public Page<Notice> findByUserAndReadStatus(@RequestParam Long userId, @RequestParam Boolean readStatus,
		@RequestParam Pageable pageable) {
		return service.findByUserAndReadStatus(userId, readStatus, pageable);
	}
}
