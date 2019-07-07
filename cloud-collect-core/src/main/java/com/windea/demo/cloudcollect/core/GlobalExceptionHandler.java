package com.windea.demo.cloudcollect.core;

import com.windea.demo.cloudcollect.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理器。
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(InvalidUserException.class)
	ResponseEntity<?> handleInvalidUserException(InvalidUserException e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<?> handleNotFoundException(NotFoundException e) {
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
