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
 * 自定义的许可鉴别器。基于实体类属性。判断此属性是否等于principal.name，且对应的permission是否相匹配。
 * <p>用于实现Spring El中的hasPermission()方法。
 * <p>TODO 加入缓存控制，提高扩展性。
 */
@Component
public class PropertyBasedPermissionEvaluator implements PermissionEvaluator {
	public final static Set<String> allPermissionNames = Set.of("read", "write", "create", "delete", "admin");
	public final static String targetDomainPackage = "com.windea.demo.cloudcollect.core.domain.entity";

	private final CollectService collectService;
	private final CollectCategoryService categoryService;
	private final CollectTagService tagService;
	private final CommentService commentService;
	private final NoticeService noticeService;

	public PropertyBasedPermissionEvaluator(CollectService collectService, CollectCategoryService categoryService,
		CollectTagService tagService, CommentService commentService, NoticeService noticeService) {
		this.collectService = collectService;
		this.categoryService = categoryService;
		this.tagService = tagService;
		this.commentService = commentService;
		this.noticeService = noticeService;
	}


	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		var principal = "";
		var permissions = Set.of();

		if(targetDomainObject instanceof Collect) {
			principal = ((Collect) targetDomainObject).getUser().getUsername();
			permissions = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof CollectTag) {
			principal = ((CollectTag) targetDomainObject).getUser().getUsername();
			permissions = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof CollectCategory) {
			principal = ((CollectCategory) targetDomainObject).getUser().getUsername();
			permissions = Set.of("read", "write", "create", "delete");
		} else if(targetDomainObject instanceof Comment) {
			principal = ((Comment) targetDomainObject).getSponsorByUser().getUsername();
			permissions = Set.of("read", "create", "delete");
		} else if(targetDomainObject instanceof Notice) {
			principal = ((Notice) targetDomainObject).getUser().getUsername();
			permissions = Set.of("read", "delete");
		} else {
			return false;
		}

		return Objects.equals(authentication.getName(), principal) && permissions.contains(permission);
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
