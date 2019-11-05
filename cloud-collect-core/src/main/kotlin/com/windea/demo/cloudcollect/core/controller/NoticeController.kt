@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
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
	@ApiOperation("创建一条通知。")
	@PostMapping("/create")
	fun create(@RequestBody notice: Notice, authentication: Authentication) {
		noticeService.create(notice, authentication.toUser())
	}
	
	@ApiOperation("删除一条通知。")
	@DeleteMapping("/{id}")
	fun deleteById(@PathVariable id: Long) {
		noticeService.deleteById(id)
	}
	
	@ApiOperation("删除某一用户的所有浏览记录。")
	@DeleteMapping("/deleteAllByUserId")
	fun deleteAllByUserId(@RequestParam userId: Long) {
		noticeService.deleteAllByUserId(userId)
	}
	
	@ApiOperation("得到某一用户的所有通知。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<Notice> {
		return noticeService.findAllByUserId(userId, pageable)
	}
}
