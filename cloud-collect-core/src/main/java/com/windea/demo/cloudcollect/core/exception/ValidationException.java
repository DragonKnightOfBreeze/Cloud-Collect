package com.windea.demo.cloudcollect.core.exception;

import com.windea.demo.cloudcollect.core.domain.enums.ResponseResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 参数校验的异常。
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 7783299109810934473L;

	private List<ObjectError> validationErrors;

	public ValidationException(List<ObjectError> validationErrors) {
		super(ResponseResult.VALIDATION_ERROR.toString());
		this.validationErrors = validationErrors;
	}

	public ValidationException(List<ObjectError> validationErrors, Throwable throwable) {
		super(ResponseResult.VALIDATION_ERROR.toString(), throwable);
		this.validationErrors = validationErrors;
	}


	public List<ObjectError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ObjectError> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
