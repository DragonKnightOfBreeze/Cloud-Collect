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
}
