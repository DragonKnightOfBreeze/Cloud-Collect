package com.windea.demo.cloudcollect.core.enums

/**响应结果的状态。*/
enum class ResultStatus(
	val message: String
) {
	OK("操作成功。"),
	USER_NOT_FOUND("用户不存在！"),
	INVALID_USER("用户名或密码错误！"),
	INVALID_AUTH_CODE("验证码错误！"),
	IMPORT_DATA_FAILED("导入数据失败！"),
	EXPORT_DATA_FAILED("导出数据失败！"),
	UPLOAD_AVATAR_FAILED("上传用户头像失败！"),
	NOT_FOUND("页面不存在！"),
	NOT_IMPLEMENTED("功能未实现！")
}
