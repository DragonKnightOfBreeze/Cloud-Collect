package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.stereotype.*

@Service
class UrlCopyServiceImpl : UrlCopyService {
	override fun copyAsHtmlUrl(collect: Collect): Url {
		return UrlGenerator.generateHtmlUrl(collect.name, collect.url, collect.summary).toUrl()
	}
	
	override fun copyAsMdUrl(collect: Collect): Url {
		return UrlGenerator.generateMdUrl(collect.name, collect.url, collect.summary).toUrl()
	}
	
	override fun copyAsMdRefUrl(collect: Collect): Url {
		return UrlGenerator.generateMdRefUrl("url${collect.id}", collect.name, collect.url, collect.summary).toUrl()
	}
	
	override fun copyAsMdImgUrl(collect: Collect): Url {
		return UrlGenerator.generateMdImgUrl(collect.name, collect.url, collect.summary).toUrl()
	}
	
	override fun copyAsMdImgRefUrl(collect: Collect): Url {
		return UrlGenerator.generateMdImgRefUrl("url${collect.id}", collect.name, collect.url, collect.summary).toUrl()
	}
	
	fun String.toUrl() = Url(this)
}
