package com.windea.demo.cloudcollect.core.exception;

/**
 * 页面未找到的异常。
 */
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3747208892156399108L;

	private static final String message = "404 页面未找到！";

	public NotFoundException() {
		super(message);
	}

	public NotFoundException(Throwable throwable) {
		super(message, throwable);
	}
}
