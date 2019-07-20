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
		val principal: String?
		val permissions: Set<String>
		
		when(targetDomainObject) {
			is Collect -> {
				principal = targetDomainObject.user.username
				permissions = setOf("read", "write", "create", "delete")
			}
			is CollectTag -> {
				principal = targetDomainObject.user.username
				permissions = setOf("read", "write", "create", "delete")
			}
			is CollectCategory -> {
				principal = targetDomainObject.user.username
				permissions = setOf("read", "write", "create", "delete")
			}
			is Comment -> {
				principal = targetDomainObject.sponsorByUser.username
				permissions = setOf("read", "create", "delete")
			}
			is Notice -> {
				principal = targetDomainObject.user.username
				permissions = setOf("read", "delete")
			}
			else -> return false
		}
		
		return authentication.name == principal && permission in permissions
	}
	
	override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
		val targetDomainObject: Any
		
		when(targetType) {
			"Collect" -> targetDomainObject = collectService.get(targetId as Long)
			"CollectCategory" -> targetDomainObject = categoryService.get(targetId as Long)
			"CollectTag" -> targetDomainObject = tagService.get(targetId as Long)
			"Comment" -> targetDomainObject = commentService.get(targetId as Long)
			"Notice" -> targetDomainObject = noticeService.get(targetId as Long)
			else -> return false
		}
		
		return hasPermission(authentication, targetDomainObject, permission)
	}
}
