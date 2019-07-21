package com.windea.demo.cloudcollect.core.domain.enums

/**响应结果。*/
enum class ResponseResult(
	val code: Int,
	val message: String
) {
	OK(200, "200 操作成功。"),
	USER_NOT_FOUND(401, "401 用户未找到！"),
	INVALID_USER(403, "403 非法的用户！"),
	NOT_FOUND(404, "404 页面未找到！"),
	NOT_IMPLEMENTED(501, "501 功能未实现！"),
	VALIDATION_ERROR(400, "400 参数错误！")
}
