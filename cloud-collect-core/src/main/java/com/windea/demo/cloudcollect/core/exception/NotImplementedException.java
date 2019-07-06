package com.windea.demo.cloudcollect.core.exception;

/**
 * 功能未实现的异常。
 */
public class NotImplementedException extends RuntimeException {
	private static final long serialVersionUID = 3747208892156399108L;

	private static final String message = "501 功能未实现！";

	public NotImplementedException() {
		super(message);
	}

	public NotImplementedException(Throwable throwable) {
		super(message, throwable);
	}
}
