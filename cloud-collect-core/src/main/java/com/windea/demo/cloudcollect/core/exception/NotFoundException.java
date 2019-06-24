package com.windea.demo.cloudcollect.core.exception;

import com.windea.demo.cloudcollect.core.domain.enums.ResponseResult;

/**
 * 内容未找到的异常。
 */
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7983528822288468104L;

	public NotFoundException() {
		super(ResponseResult.NOT_FOUND.toString());
	}

	public NotFoundException(Throwable throwable) {
		super(ResponseResult.NOT_FOUND.toString(), throwable);
	}
}
