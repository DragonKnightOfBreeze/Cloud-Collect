package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*

/**导入收藏数据的异常。*/
class ImportDataException : RuntimeException {
	constructor() : super(ResponseResult.IMPORT_DATA_FAILED.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.IMPORT_DATA_FAILED.message, throwable)
}
