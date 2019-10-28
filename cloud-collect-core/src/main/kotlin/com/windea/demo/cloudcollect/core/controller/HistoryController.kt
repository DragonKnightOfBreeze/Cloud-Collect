package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.data.domain.*
import org.springframework.security.access.prepost.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*

@Api("浏览记录")
@RestController
@RequestMapping("/history")
@CrossOrigin
class HistoryController(
	private val historyService: HistoryService
) {
	//NOTE 浏览记录的创建交由前端
	@ApiOperation("创建浏览记录。")
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	fun create(@RequestBody history: History, authentication: Authentication): History {
		val user = (authentication.principal as UserDetailsVo).delegateUser
		return historyService.create(history, user)
	}
	
	@ApiOperation("删除一条浏览记录。")
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	fun deleteById(@PathVariable id: Long) {
		return historyService.deleteById(id)
	}
	
	@ApiOperation("删除某一用户的所有浏览记录。")
	@DeleteMapping("/deleteAllByUserId")
	@PreAuthorize("isAuthenticated()")
	fun deleteAllByUserId(@RequestParam userId: Long) {
		return historyService.deleteAllByUserId(userId)
	}
	
	@ApiOperation("得到某一用户的所有浏览记录。")
	@GetMapping("/findAllByUserId")
	fun findAllByUserId(@RequestParam userId: Long, pageable: Pageable): Page<History> {
		return historyService.findAllByUserId(userId, pageable)
	}
}
