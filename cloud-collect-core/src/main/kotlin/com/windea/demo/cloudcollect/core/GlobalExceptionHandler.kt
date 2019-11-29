package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.exceptions.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

/**全局异常处理器。*/
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
	@ExceptionHandler(ValidationException::class)
	fun handleValidationException(e: ValidationException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
	}
	
	@ExceptionHandler(ExportDataException::class)
	fun handleExportDataException(e: ExportDataException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
	}
	
	@ExceptionHandler(ImportDataException::class)
	fun handleImportDataException(e: ImportDataException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
	}
	
	@ExceptionHandler(UserNotFoundException::class)
	fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
	}
	
	@ExceptionHandler(InvalidUserException::class)
	fun handleInvalidUserException(e: InvalidUserException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
	}
	
	@ExceptionHandler(InvalidAuthCodeException::class)
	fun handleInvalidAuthCodeException(e: InvalidAuthCodeException): ResponseEntity<String> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e.message)
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
}
