package com.windea.demo.cloudcollect.core.controller

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.*

@Api("链接复制")
@RestController
@RequestMapping("/urlCopy")
@CrossOrigin
class UrlCopyController(
	private val urlCopyService: UrlCopyService
) {
	@ApiOperation("复制为Html链接。")
	@PostMapping("/copyAsHtmlUrl")
	fun copyAsHtmlUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsHtmlUrl(collect)
	}
	
	@ApiOperation("复制为Markdown链接。")
	@PostMapping("/copyAsMdUrl")
	fun copyAsMdUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsMdUrl(collect)
	}
	
	@ApiOperation("复制为Markdown引用链接。")
	@PostMapping("/copyAsMdRefUrl")
	fun copyAsMdRefUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsMdRefUrl(collect)
	}
	
	@ApiOperation("复制为Markdown图片链接。")
	@PostMapping("/copyAsMdImgUrl")
	fun copyAsMdImgUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsMdImgUrl(collect)
	}
	
	@ApiOperation("复制为Markdown图片引用链接。")
	@PostMapping("/copyAsMdImgRefUrl")
	fun copyAsMdImgRefUrl(@RequestBody collect: Collect): String {
		return urlCopyService.copyAsMdImgRefUrl(collect)
	}
}
