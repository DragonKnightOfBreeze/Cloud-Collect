package com.windea.demo.cloudcollect.core.domain.enums;

/**
 * 响应结果。
 */
public enum ResponseResult {
	SUCCESS(200, "操作成功"),
	VALIDATION_ERROR(400, "参数错误"),
	NOT_LOGIN(401, "未登录"),
	PERMISSION_ERROR(403, "权限错误"),
	NOT_FOUND(404, "内容未找到"),
	INTERNAL_ERROR(500, "内部错误"),
	NOT_IMPLEMENTED(501, "功能未实现"),

	USER_DUPLICATE(4001, "用户已存在"),
	COLLECT_DUPLICATE(4002, "收藏已存在");


	private final int code;
	private final String message;

	ResponseResult(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public int code() {
		return code;
	}

	public String message() {
		return message;
	}

	@Override
	public String toString() {
		return this.code + " " + this.message;
	}
}
