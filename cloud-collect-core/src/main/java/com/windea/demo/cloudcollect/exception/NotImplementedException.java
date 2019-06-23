package com.windea.demo.cloudcollect.exception;

/**
 * 功能未实现的异常。
 */
public class NotImplementedException extends RuntimeException {
	private static final long serialVersionUID = 3747208892156399108L;

	public NotImplementedException() {
		super();
	}

	public NotImplementedException(String message) {
		super(message);
	}

	public NotImplementedException(Throwable throwable) {
		super(throwable);
	}

	public NotImplementedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
