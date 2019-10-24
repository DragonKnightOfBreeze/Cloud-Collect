package com.windea.demo.cloudcollect.core.extensions

/**地址的生成器。*/
object UrlGenerator {
	var useDoubleQuote = true
	
	/**根据指定参数生成Html链接。*/
	fun generateHtmlUrl(name: String, url: String, title: String? = null): String {
		val quote = if(useDoubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " title=$quote$title$quote" } ?: ""
		return "<a href=$quote$url$quote$titleSnippet>$name</a>"
	}
	
	/**根据指定参数生成Markdown链接。*/
	fun generateMdUrl(name: String, url: String, title: String? = null): String {
		val quote = if(useDoubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$name]($url$titleSnippet)"
	}
	
	/**根据指定参数生成Markdown引用链接。*/
	fun generateMdRefUrl(id: String, name: String, url: String, title: String? = null): Pair<String, String> {
		val quote = if(useDoubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$name][$id]" to "[$id]: $url$titleSnippet"
	}
	
	/**根据指定参数生成Markdown图片链接。*/
	fun generateMdImgUrl(name: String, url: String, title: String? = null): String {
		return generateMdUrl(name, url, title).let { "!$it" }
	}
	
	/**根据指定参数生成Markdown图片引用链接。*/
	fun generateMdImgRefUrl(id: String, name: String, url: String, title: String? = null): Pair<String, String> {
		return generateMdRefUrl(id, name, url, title).let { "！${it.first}" to it.second }
	}
}
