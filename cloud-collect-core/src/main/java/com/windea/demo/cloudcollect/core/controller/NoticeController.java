package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.Notice;
import com.windea.demo.cloudcollect.core.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 通知的控制器。
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
	private final NoticeService service;

	public NoticeController(NoticeService service) {
		this.service = service;
	}


	//不允许用户自行创建通知

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PutMapping("/{id}/read")
	public void read(@PathVariable Long id) {
		service.read(id);
	}

	@GetMapping("/{id}")
	public Notice get(@PathVariable Long id) {
		return service.get(id);
	}

	@GetMapping("/findAll")
	public Page<Notice> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/findByUser")
	public Page<Notice> findByUser(@RequestParam Long userId, @RequestParam Pageable pageable) {
		return service.findByUser(userId, pageable);
	}

	@GetMapping("/findByUserAndReadStatus")
	public Page<Notice> findByUserAndReadStatus(@RequestParam Long userId, @RequestParam Boolean readStatus,
		@RequestParam Pageable pageable) {
		return service.findByUserAndReadStatus(userId, readStatus, pageable);
	}
}
