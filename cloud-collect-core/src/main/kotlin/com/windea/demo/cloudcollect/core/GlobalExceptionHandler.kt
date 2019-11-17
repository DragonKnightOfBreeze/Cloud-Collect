package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.exceptions.*
import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

/**全局异常处理器。*/
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
	@ExceptionHandler(ValidationException::class)
	fun handleValidationException(e: ValidationException): ResponseEntity<ValidationException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
	
	@ExceptionHandler(ExportDataException::class)
	fun handleExportDataException(e: ExportDataException): ResponseEntity<ExportDataException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
	
	@ExceptionHandler(ImportDataException::class)
	fun handleImportDataException(e: ImportDataException): ResponseEntity<ImportDataException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
	
	@ExceptionHandler(UserNotFoundException::class)
	fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<UserNotFoundException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
	
	@ExceptionHandler(IncorrectAuthCodeException::class)
	fun handleIncorrectAuthCodeException(e: IncorrectAuthCodeException): ResponseEntity<IncorrectAuthCodeException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
	
	@ExceptionHandler(InvalidUserException::class)
	fun handleInvalidUserException(e: InvalidUserException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
	}
	
	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(e: NotFoundException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.notFound().build()
	}
	
	@ExceptionHandler(NotImplementedException::class)
	fun handleNotImplementedException(e: NotImplementedException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
	}
	
	@ExceptionHandler(AuthenticationException::class)
	fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
	}
}
