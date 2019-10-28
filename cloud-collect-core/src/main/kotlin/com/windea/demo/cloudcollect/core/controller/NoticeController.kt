@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.data.web.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*

@Api("通知")
@RestController
@RequestMapping("/notice")
@CrossOrigin
class NoticeController(
	private val noticeService: NoticeService
) {
	//NOTE 通知的创建交由前端
	@ApiOperation("创建某一用户的通知。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody notice: Notice, authentication: Authentication) {
		noticeService.create(notice, authentication.toUser())
	}
	
	@ApiOperation("阅读自己的通知。")
	@PutMapping("/{id}/read")
	@PreAuthorize("isAuthenticated()")
	fun read(@PathVariable id: Long, @RequestBody notice: Notice) {
		noticeService.read(notice)
	}
	
	@ApiOperation("删除自己的通知。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		noticeService.deleteById(id)
	}
	
	@ApiOperation("得到某一通知。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Notice {
		return noticeService.findById(id)
	}
	
	@ApiOperation("得到所有通知。")
	@GetMapping("/findAll")
	fun findAll(@PageableDefault pageable: Pageable): Page<Notice> {
		return noticeService.findAll(pageable)
	}
	
	@ApiOperation("查询某一用户的所有通知。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, @PageableDefault pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("查询某一用户的所有已读/未读通知。")
	@GetMapping("/findAllByUserIdAndRead")
	fun findAllByUserIdAndRead(@RequestParam userId: Long, @RequestParam readStatus: Boolean, @PageableDefault pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserIdAndRead(userId, readStatus, pageable)
	}
}
