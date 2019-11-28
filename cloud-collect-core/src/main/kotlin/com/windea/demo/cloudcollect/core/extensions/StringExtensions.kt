package com.windea.demo.cloudcollect.core.extensions

import java.net.*

fun String.toNoQueryUrl() = this.substringBefore("?")

fun String.toLogoUrl() = runCatching { this.replaceAfter(URL(this).host, "/favicon.ico") }.getOrDefault("")
