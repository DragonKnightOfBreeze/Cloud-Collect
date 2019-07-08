package com.windea.demo.cloudcollect.core.component;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.*;

/**
 * 基于实体属性的许可鉴别器。
 */
@SuppressWarnings("unchecked")
public interface PropertyBasedPermissionEvaluator extends PermissionEvaluator {
	@Override
	default boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return false;
	}

	@Override
	default boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
		Object permission) {
		return false;
	}


	/**
	 * 得到适用的实体类列表。
	 */
	default List<Class<? extends Serializable>> getTargetDomainClasses(List<Class<? extends Serializable>> classList) {
		return List.of();
	}

	/**
	 * 转换许可。
	 */
	default boolean convertPermission(Object permission, Object actualPermission) {
		try {
			var permissions = Arrays.stream(permission.toString().split(",")).map(String::trim).toArray();
			var actualPermissions = (Set<String>) actualPermission;
			return actualPermissions.containsAll(Arrays.asList(permissions));
		} catch(Exception e) {
			return false;
		}
	}
}


@FunctionalInterface
interface IdToTargetDomain<T extends Serializable, ID extends Serializable> {
	T getTargetDomain(ID id);
}

@FunctionalInterface
interface DomainToPrincipal<T extends Serializable> {
	String getPrincipal(T domain);
}

@FunctionalInterface
interface PrincipalToPermissions {
	Set<String> getPermissions(String principal);
}


