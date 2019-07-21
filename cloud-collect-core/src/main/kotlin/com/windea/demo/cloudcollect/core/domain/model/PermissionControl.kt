package com.windea.demo.cloudcollect.core.domain.model

/**实体类访问权限控制的接口。用于获得对应的权限组，并检查是否拥有权限。*/
interface PermissionControl {
	val permissions: Set<Permission>
}