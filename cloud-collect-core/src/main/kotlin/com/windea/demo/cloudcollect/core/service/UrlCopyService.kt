package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*

interface UrlCopyService {
	/**复制为Html链接。*/
	fun copyAsHtmlUrl(collect: Collect): Url
	
	/**复制为Markdown链接。*/
	fun copyAsMdUrl(collect: Collect): Url
	
	/**复制为Markdown引用连接。*/
	fun copyAsMdRefUrl(collect: Collect): Url
	
	/**复制为Markdown图片链接。*/
	fun copyAsMdImgUrl(collect: Collect): Url
	
	/**复制为Markdown图片引用连接。*/
	fun copyAsMdImgRefUrl(collect: Collect): Url
}
