package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*

/**复制链接的服务。*/
interface UrlCopyService {
	/**复制为Html链接。*/
	fun copyAsHtmlUrl(collect: Collect): String
	
	/**复制为Markdown链接。*/
	fun copyAsMdUrl(collect: Collect): String
	
	/**复制为Markdown引用连接。*/
	fun copyAsMdRefUrl(collect: Collect): Pair<String, String>
	
	/**复制为Markdown图片链接。*/
	fun copyAsMdImgUrl(collect: Collect): String
	
	/**复制为Markdown图片引用连接。*/
	fun copyAsMdImgRefUrl(collect: Collect): Pair<String, String>
}
