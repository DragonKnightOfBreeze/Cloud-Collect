package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.data.redis.core.*
import org.springframework.stereotype.*
import java.util.*

@Service
class CacheServiceImpl(
	private val redisTemplate: StringRedisTemplate,
	private val redisProperties: RedisProperties
) : CacheService {
	override fun getActivateCode(username: String): String? {
		val key = "${redisProperties.activateCodePrefix}${username}"
		return redisTemplate.opsForValue()[key]
	}
	
	override fun setActivateCode(username: String) {
		val key = "${redisProperties.activateCodePrefix}${username}"
		val value = UUID.randomUUID().toString()
		redisTemplate.opsForValue().set(key, value, redisProperties.expiration)
	}
	
	override fun getResetPasswordCode(username: String): String? {
		val key = "${redisProperties.resetPasswordCodePrefix}${username}"
		return redisTemplate.opsForValue()[key]
	}
	
	override fun setResetPasswordCode(username: String) {
		val key = "${redisProperties.resetPasswordCodePrefix}${username}"
		val value = UUID.randomUUID().toString()
		redisTemplate.opsForValue().set(key, value, redisProperties.expiration)
	}
}
