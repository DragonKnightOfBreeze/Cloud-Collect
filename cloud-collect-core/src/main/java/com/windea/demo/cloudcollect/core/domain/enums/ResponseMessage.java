package com.windea.demo.cloudcollect.core.domain.enums;

/**
 * 响应消息。
 */
public enum ResponseMessage {
	SUCCESS(200, "操作成功"),
	VALIDATION_ERROR(400, "参数错误"),
	NOT_LOGIN(401, "未登录"),
	PERMISSION_ERROR(403, "权限错误"),
	NOT_FOUND(404, "内容未找到"),
	INTERNAL_ERROR(500, "内部错误"),

	USER_DUPLICATE(4001, "用户已存在"),
	COLLECT_DUPLICATE(4002, "收藏已存在");


	private int code;
	private String message;


	ResponseMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
