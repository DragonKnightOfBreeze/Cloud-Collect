package com.windea.demo.cloudcollect.core.service

interface CacheService {
	fun getActivateCode(username: String): String?
	
	fun setActivateCode(username: String)
	
	fun getResetPasswordCode(username: String): String?
	
	fun setResetPasswordCode(username: String)
}
