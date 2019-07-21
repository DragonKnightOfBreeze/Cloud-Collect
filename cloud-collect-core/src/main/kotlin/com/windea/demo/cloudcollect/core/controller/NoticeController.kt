@file:Suppress("UNUSED_PARAMETER")

package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/**通知的控制器。*/
@Api("通知")
@RestController
@RequestMapping("/notice")
@CrossOrigin
class NoticeController(
	private val service: NoticeService
) {
	@ApiOperation("创建通知，发送给所有用户。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "notice", value = "新的通知", required = true)
	)
	@PostMapping("/sendToAll")
	@PreAuthorize("hasRole('ADMIN')")
	fun sendToAll(@RequestBody @Valid notice: Notice, bindingResult: BindingResult) {
		service.sendToAll(notice)
	}
	
	@ApiOperation("删除自己的通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id, 'Notice', 'delete')")
	fun delete(@PathVariable id: Long) {
		service.delete(id)
	}
	
	@ApiOperation("阅读自己的通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@PutMapping("/{id}/read")
	fun read(@PathVariable id: Long): Notice {
		return service.read(id)
	}
	
	@ApiOperation("得到某一通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	)
	@GetMapping("/{id}")
	fun findById(@PathVariable id: Long): Notice {
		return service.findById(id)
	}
	
	@ApiOperation("分页得到所有通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAll")
	fun findAll(@RequestParam pageable: Pageable): Page<Notice> {
		return service.findAll(pageable)
	}
	
	@ApiOperation("分页查询某一用户的所有通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findByUser")
	fun findByUser(@RequestParam userId: Long, @RequestParam pageable: Pageable): Page<Notice> {
		return service.findAllByUserId(userId, pageable)
	}
	
	@ApiOperation("分页查询某一用户的所有已读/未读通知。")
	@ApiImplicitParams(
		ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		ApiImplicitParam(name = "isRead", value = "阅读状态", required = true),
		ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	)
	@GetMapping("/findAllByUserIdAndRead")
	fun findAllByUserIdAndRead(@RequestParam userId: Long, @RequestParam isRead: Boolean, @RequestParam pageable: Pageable): Page<Notice> {
		return service.findAllByUserIdAndRead(userId, isRead, pageable)
	}
}
