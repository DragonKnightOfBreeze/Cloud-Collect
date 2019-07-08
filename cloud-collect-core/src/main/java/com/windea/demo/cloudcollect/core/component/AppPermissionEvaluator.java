package com.windea.demo.cloudcollect.core.component;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.service.*;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义许可鉴别器。用于实现Spring El中的hasPermission()方法。
 * <p>TODO 加入缓存控制，扩展成微型框架。
 */
@Component
public class AppPermissionEvaluator implements PermissionEvaluator {
	private final static Set<String> allPermissionNames = Set.of("read", "write", "create", "delete", "admin");
	private final static String targetDomainPackage = "com.windea.demo.cloudcollect.core.domain.entity";

	private final CollectService collectService;
	private final CollectCategoryService categoryService;
	private final CollectTagService tagService;
	private final CommentService commentService;
	private final NoticeService noticeService;

	public AppPermissionEvaluator(CollectService collectService, CollectCategoryService categoryService,
		CollectTagService tagService, CommentService commentService, NoticeService noticeService) {
		this.collectService = collectService;
		this.categoryService = categoryService;
		this.tagService = tagService;
		this.commentService = commentService;
		this.noticeService = noticeService;
	}


	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		var principalName = "";
		var permissionNames = Set.of();

		if(targetDomainObject instanceof Collect) {
			principalName = ((Collect) targetDomainObject).getUser().getUsername();
			permissionNames = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof CollectTag) {
			principalName = ((CollectTag) targetDomainObject).getUser().getUsername();
			permissionNames = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof CollectCategory) {
			principalName = ((CollectCategory) targetDomainObject).getUser().getUsername();
			permissionNames = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof Comment) {
			principalName = ((Comment) targetDomainObject).getSponsorByUser().getUsername();
			permissionNames = Set.of("read", "create", "delete");
		} else if(targetDomainObject instanceof Notice) {
			principalName = ((Notice) targetDomainObject).getUser().getUsername();
			permissionNames = Set.of("read", "delete");
		} else {
			return false;
		}

		return Objects.equals(authentication.getName(), principalName) && permissionNames.contains(permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
		Object permission) {
		Object targetDomainObject;

		if(Objects.equals(targetType, "Collect")) {
			targetDomainObject = collectService.get((Long) targetId);
		} else if(Objects.equals(targetType, "CollectCategory")) {
			targetDomainObject = categoryService.get((Long) targetId);
		} else if(Objects.equals(targetType, "CollectTag")) {
			targetDomainObject = tagService.get((Long) targetId);
		} else if(Objects.equals(targetType, "Comment")) {
			targetDomainObject = commentService.get((Long) targetId);
		} else if(Objects.equals(targetType, "Notice")) {
			targetDomainObject = noticeService.get((Long) targetId);
		} else {
			return false;
		}

		return hasPermission(authentication, targetDomainObject, permission);
	}
}
