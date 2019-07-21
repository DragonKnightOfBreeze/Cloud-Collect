package com.windea.demo.cloudcollect.core.domain.model

import org.springframework.security.core.*

/**实体类访问权限。*/
class Permission(
	name: String,
	checkPermission: (Authentication) -> Boolean
)
