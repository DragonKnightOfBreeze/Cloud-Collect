package com.windea.demo.cloudcollect.core.exception;

import com.windea.demo.cloudcollect.core.domain.enums.ResponseResult;

/**
 * 功能未实现的异常。
 */
public class NotImplementedException extends RuntimeException {
	private static final long serialVersionUID = 3747208892156399108L;

	public NotImplementedException() {
		super(ResponseResult.NOT_IMPLEMENTED.toString());
	}

	public NotImplementedException(Throwable throwable) {
		super(ResponseResult.NOT_IMPLEMENTED.toString(), throwable);
	}
}
