package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.exceptions.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

//由于不知道如何解决直接返回普通字符串时的乱码问题，只好这样了……

/**全局异常处理器。*/
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
	@ExceptionHandler(ValidationException::class)
	fun handleValidationException(e: ValidationException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(ExportDataException::class)
	fun handleExportDataException(e: ExportDataException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(ImportDataException::class)
	fun handleImportDataException(e: ImportDataException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(UploadAvatarException::class)
	fun handleUploadAvatarException(e: UploadAvatarException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(UserNotFoundException::class)
	fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(InvalidUserException::class)
	fun handleInvalidUserException(e: InvalidUserException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(InvalidAuthCodeException::class)
	fun handleInvalidAuthCodeException(e: InvalidAuthCodeException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(e: NotFoundException): ResponseEntity<Message> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(Message(e.message))
	}
	
	@ExceptionHandler(NotImplementedException::class)
	fun handleNotImplementedException(e: NotImplementedException): ResponseEntity<Nothing> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
	}
}
