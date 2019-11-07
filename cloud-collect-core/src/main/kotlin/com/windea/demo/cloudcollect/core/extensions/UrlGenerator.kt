package com.windea.demo.cloudcollect.core.extensions

/**地址的生成器。*/
object UrlGenerator {
	private const val quote = "\""
	
	/**根据指定参数生成Html链接。*/
	fun generateHtmlUrl(name: String, url: String, title: String? = null): String {
		val titleSnippet = if(title.isNullOrBlank()) "" else " title=$quote$title$quote"
		return "<a href=$quote$url$quote$titleSnippet>$name</a>"
	}
	
	/**根据指定参数生成Markdown链接。*/
	fun generateMdUrl(name: String, url: String, title: String? = null): String {
		val titleSnippet = if(title.isNullOrBlank()) "" else " $quote$title$quote"
		return "[$name]($url$titleSnippet)"
	}
	
	/**根据指定参数生成Markdown引用链接。*/
	fun generateMdRefUrl(id: String, name: String, url: String, title: String? = null): String {
		val titleSnippet = if(title.isNullOrBlank()) "" else " $quote$title$quote"
		return "[$name][$id]\n[$id]: $url$titleSnippet"
	}
	
	/**根据指定参数生成Markdown图片链接。*/
	fun generateMdImgUrl(name: String, url: String, title: String? = null): String {
		return generateMdUrl(name, url, title).let { "!$it" }
	}
	
	/**根据指定参数生成Markdown图片引用链接。*/
	fun generateMdImgRefUrl(id: String, name: String, url: String, title: String? = null): String {
		return generateMdRefUrl(id, name, url, title).let { "!$it" }
	}
}
