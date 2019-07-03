package com.windea.demo.cloudcollect.core.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 参数校验的异常。
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 7783299109810934473L;

	private static final String message = "200 参数错误！";

	private List<ObjectError> validationErrors;

	public ValidationException(List<ObjectError> validationErrors) {
		super(message);
		this.validationErrors = validationErrors;
	}

	public ValidationException(List<ObjectError> validationErrors, Throwable throwable) {
		super(message, throwable);
		this.validationErrors = validationErrors;
	}


	public List<ObjectError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ObjectError> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
