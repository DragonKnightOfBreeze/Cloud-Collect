package com.windea.demo.cloudcollect.core.domain.model

import org.springframework.security.core.*

/**实体类访问权限。*/
@Deprecated("使用基于属性的简单访问权限控制。")
class Permission(
	name: String,
	checkPermission: (Authentication) -> Boolean
)
