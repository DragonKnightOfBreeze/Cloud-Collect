package com.windea.demo.cloudcollect.core.component

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.security.access.*
import org.springframework.security.core.*
import org.springframework.stereotype.*
import java.io.*

/**
 * 自定义的许可鉴别器。基于实体类属性。判断此属性是否等于principal.name，且对应的permission是否相匹配。
 *
 * 用于实现Spring El中的hasPermission()方法。
 *
 * TODO 加入缓存控制，提高扩展性。
 */
@Component
class PropertyBasedPermissionEvaluator(
	private val collectService: CollectService,
	private val categoryService: CollectCategoryService,
	private val tagService: CollectTagService,
	private val commentService: CommentService,
	private val noticeService: NoticeService
) : PermissionEvaluator {
	override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
		val permissions: Set<String> = when(targetDomainObject) {
			is Collect -> setOf("read", "write", "create", "delete")
			is CollectTag -> setOf("read", "write", "create", "delete")
			is CollectCategory -> setOf("read", "write", "create", "delete")
			is Comment -> setOf("read", "create", "delete")
			is Notice -> setOf("read", "create", "delete")
			else -> return false
		}
		
		if(permission !in permissions) return false
		if(permission == "create") return true
		
		val principalName: String = when(targetDomainObject) {
			is Collect -> targetDomainObject.user.username
			is CollectTag -> targetDomainObject.user.username
			is CollectCategory -> targetDomainObject.user.username
			is Comment -> targetDomainObject.sponsorByUser.username
			is Notice -> targetDomainObject.user.username
			else -> return false
		}
		
		return principalName == authentication.name
	}
	
	override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
		val permissions: Set<String> = when(targetType) {
			"Collect" -> setOf("read", "write", "create", "delete")
			"CollectTag" -> setOf("read", "write", "create", "delete")
			"CollectCategory" -> setOf("read", "write", "create", "delete")
			"Comment" -> setOf("read", "create", "delete")
			"Notice" -> setOf("read", "create", "delete")
			else -> return false
		}
		
		if(permission !in permissions) return false
		if(permission == "create") return true
		
		val targetDomainObject = when(targetType) {
			"Collect" -> collectService.findById(targetId as Long)
			"CollectCategory" -> categoryService.findById(targetId as Long)
			"CollectTag" -> tagService.findById(targetId as Long)
			"Comment" -> commentService.findById(targetId as Long)
			"Notice" -> noticeService.findById(targetId as Long)
			else -> return false
		}
		
		val principalName: String = when(targetDomainObject) {
			is Collect -> targetDomainObject.user.username
			is CollectTag -> targetDomainObject.user.username
			is CollectCategory -> targetDomainObject.user.username
			is Comment -> targetDomainObject.sponsorByUser.username
			is Notice -> targetDomainObject.user.username
			else -> return false
		}
		
		return principalName == authentication.name
	}
}
