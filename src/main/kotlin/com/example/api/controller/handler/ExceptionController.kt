package com.example.api.controller.handler

import com.example.api.usecase.dto.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponseDTO> {
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponseDTO(
            status = httpStatus.value(),
            message = ex.localizedMessage ?: "An unexpected error occurred"
        )
        return ResponseEntity(errorResponse, httpStatus)
    }
}