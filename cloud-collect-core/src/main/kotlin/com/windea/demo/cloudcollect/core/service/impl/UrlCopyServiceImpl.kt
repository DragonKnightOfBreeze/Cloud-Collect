package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.generators.string.*
import org.springframework.stereotype.*

@Service
class UrlCopyServiceImpl : UrlCopyService {
	override fun copyAsHtmlUrl(collect: Collect): String {
		return UrlGenerator.generateHtmlUrl(collect.name, collect.url, collect.summary)
	}
	
	override fun copyAsMdUrl(collect: Collect): String {
		return UrlGenerator.generateMdUrl(collect.name, collect.url, collect.summary)
	}
	
	override fun copyAsMdRefUrl(collect: Collect): Pair<String, String> {
		return UrlGenerator.generateMdRefUrl("url${collect.id}", collect.name, collect.url, collect.summary)
	}
}
