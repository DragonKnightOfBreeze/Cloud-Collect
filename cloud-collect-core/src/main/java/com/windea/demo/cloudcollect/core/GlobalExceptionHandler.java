package com.windea.demo.cloudcollect.core;

import com.windea.demo.cloudcollect.core.exception.NotImplementedException;
import com.windea.demo.cloudcollect.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * 全局异常处理器。
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
		e.printStackTrace();
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(NotImplementedException.class)
	ResponseEntity<?> handleNotImplementedException(NotImplementedException e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@ExceptionHandler(ValidationException.class)
	ResponseEntity<?> handleValidationException(ValidationException e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e);
	}
}
