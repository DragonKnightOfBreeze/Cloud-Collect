package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.exceptions.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

/**全局异常处理器。*/
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
	@ExceptionHandler(ExportDataException::class)
	fun handleExportDataException(e: ExportDataException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.badRequest().build()
	}
	
	@ExceptionHandler(ImportDataException::class)
	fun handleImportDataException(e: ImportDataException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.badRequest().build()
	}
	
	@ExceptionHandler(IncorrectAuthCodeException::class)
	fun handleIncorrectAuthCodeException(e: IncorrectAuthCodeException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.badRequest().build()
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
	
	@ExceptionHandler(ValidationException::class)
	fun handleValidationException(e: ValidationException): ResponseEntity<ValidationException> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
}
