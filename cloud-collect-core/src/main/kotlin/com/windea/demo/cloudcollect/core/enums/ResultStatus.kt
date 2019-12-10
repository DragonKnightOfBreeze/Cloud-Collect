package com.windea.demo.cloudcollect.core.enums

/**响应结果的状态。*/
enum class ResultStatus(
	val code: Int,
	val message: String
) {
	OK(200, "操作成功。"),
	USER_NOT_FOUND(400, "用户不存在！"),
	INVALID_USER(400, "用户名或密码错误！"),
	INVALID_AUTH_CODE(400, "验证码错误！"),
	IMPORT_DATA_FAILED(400, "导入数据失败！"),
	EXPORT_DATA_FAILED(400, "导出数据失败！"),
	NOT_FOUND(404, "页面不存在！"),
	NOT_IMPLEMENTED(501, "功能未实现！")
}
