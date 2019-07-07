package com.windea.demo.cloudcollect.core.exception;

/**
 * 非法的用户的异常。
 */
public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = 867810939053316512L;

	private static final String message = "403 非法的用户！";

	public InvalidUserException() {
		super(message);
	}

	public InvalidUserException(Throwable throwable) {
		super(message, throwable);
	}
}
