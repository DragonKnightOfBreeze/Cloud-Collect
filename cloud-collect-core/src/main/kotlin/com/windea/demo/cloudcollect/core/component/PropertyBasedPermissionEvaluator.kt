package com.windea.demo.cloudcollect.core.component

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.security.access.*
import org.springframework.security.core.*
import org.springframework.stereotype.*
import java.io.*

/**
 * 自定义的访问权限鉴别器。基于实体类属性。判断此属性是否等于principal.name，且对应的permission是否相匹配。
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
		return when(targetDomainObject) {
			is Collect -> when(permission) {
				"read" -> true
				"write" -> targetDomainObject.user.username == authentication.name
				"create" -> authentication.isAuthenticated
				"delete" -> targetDomainObject.user.username == authentication.name
				else -> false
			}
			is CollectTag -> when(permission) {
				"read" -> true
				"write" -> targetDomainObject.user.username == authentication.name
				"create" -> authentication.isAuthenticated
				"delete" -> targetDomainObject.user.username == authentication.name
				else -> false
			}
			is CollectCategory -> when(permission) {
				"read" -> true
				"write" -> targetDomainObject.user.username == authentication.name
				"create" -> authentication.isAuthenticated
				"delete" -> targetDomainObject.user.username == authentication.name
				else -> false
			}
			is Comment -> when(permission) {
				"read" -> true
				"write" -> false
				"create" -> authentication.isAuthenticated
				"delete" -> targetDomainObject.sponsorByUser.username == authentication.name
				else -> false
			}
			is Notice -> when(permission) {
				"read" -> true
				"write" -> "ADMIN" in authentication.authorities.map { it.authority }
				"create" -> "ADMIN" in authentication.authorities.map { it.authority }
				"delete" -> targetDomainObject.user.username == authentication.name
				else -> false
			}
			else -> false
		}
	}
	
	override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String,
		permission: Any): Boolean {
		val targetDomainObject = when(targetType) {
			"Collect" -> collectService.findById(targetId as Long)
			"CollectCategory" -> categoryService.findById(targetId as Long)
			"CollectTag" -> tagService.findById(targetId as Long)
			"Comment" -> commentService.findById(targetId as Long)
			"Notice" -> noticeService.findById(targetId as Long)
			else -> return false
		}
		return hasPermission(authentication, targetDomainObject, permission)
	}
}
