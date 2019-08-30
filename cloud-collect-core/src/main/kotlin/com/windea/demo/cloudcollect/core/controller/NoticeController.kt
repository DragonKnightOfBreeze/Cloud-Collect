@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

/**通知的控制器。*/
@Api("通知")
@RestController
@RequestMapping("/notice")
@CrossOrigin
class NoticeController(
	private val noticeService: NoticeService
) {
	@ApiOperation("创建通知，发送给所有用户。")
	@PostMapping("/sendToAll")
	@PreAuthorize("hasRole('ADMIN')")
	fun sendToAll(@RequestBody @Validated notice: Notice, bindingResult: BindingResult) {
		noticeService.sendToAll(notice)
	}
	
	@ApiOperation("删除自己的通知。")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Notice', 'delete')")
	fun delete(@PathVariable id: Long) {
		noticeService.delete(id)
	}
	
	@ApiOperation("阅读自己的通知。")
	@PutMapping("/{id}/read")
	fun read(@PathVariable id: Long): Notice {
		return noticeService.read(id)
	}
	
	@ApiOperation("得到某一通知。")
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Notice {
		return noticeService.findById(id)
	}
	
	@ApiOperation("得到所有通知。")
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Notice> {
		return noticeService.findAll(pageable)
	}
	
	@ApiOperation("查询某一用户的所有通知。")
	@GetMapping("/findByUser")
	fun findByUser(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("查询某一用户的所有已读/未读通知。")
	@GetMapping("/findAllByUserIdAndRead")
	fun findAllByUserIdAndRead(@RequestParam userId: Long, @RequestParam readStatus: Boolean, @RequestParam pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserIdAndRead(userId, readStatus, pageable)
	}
}
