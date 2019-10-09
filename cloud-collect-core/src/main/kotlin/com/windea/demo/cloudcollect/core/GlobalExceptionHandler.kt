package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.exceptions.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

/**全局异常处理器。*/
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
	@ExceptionHandler(ExportDataException::class)
	fun handleExportDataException(e: ExportDataException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.badRequest().build<Any>()
	}
	
	@ExceptionHandler(ImportDataException::class)
	fun handleImportDataException(e: ImportDataException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.badRequest().build<Any>()
	}
	
	@ExceptionHandler(InvalidUserException::class)
	fun handleInvalidUserException(e: InvalidUserException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build<Any>()
	}
	
	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(e: NotFoundException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.notFound().build<Any>()
	}
	
	@ExceptionHandler(NotImplementedException::class)
	fun handleNotImplementedException(e: NotImplementedException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build<Any>()
	}
	
	@ExceptionHandler(ValidationException::class)
	fun handleValidationException(e: ValidationException): ResponseEntity<*> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(e)
	}
}
