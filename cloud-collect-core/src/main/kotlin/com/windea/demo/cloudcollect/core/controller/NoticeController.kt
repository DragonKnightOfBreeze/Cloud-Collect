@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@Api("通知")
@RestController
@RequestMapping("/notice")
@CrossOrigin
class NoticeController(
	private val noticeService: NoticeService
) {
	//@ApiOperation("创建一条通知。")
	//@PostMapping("/create")
	//@PreAuthorize("isAuthenticated()")
	//fun create(@RequestBody notice: Notice) {
	//	noticeService.create(notice)
	//}
	
	@ApiOperation("删除一条通知。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		noticeService.deleteById(id)
	}
	
	@ApiOperation("删除某一用户的所有浏览记录。")
	@DeleteMapping("/deleteAllByUserId")
	@PreAuthorize("isAuthenticated()")
	fun deleteAllByUserId(@RequestParam userId: Long) {
		noticeService.deleteAllByUserId(userId)
	}
	
	@ApiOperation("得到某一用户的所有通知。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserId(userId, pageable)
	}
}
