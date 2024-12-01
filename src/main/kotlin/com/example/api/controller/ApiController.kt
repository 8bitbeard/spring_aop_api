package com.example.api.controller

import com.example.api.client.dto.ApiClientResponseDTO
import com.example.api.usecase.ApiUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@Controller
class ApiController(
    private val apiUseCase: ApiUseCase
) {

    @GetMapping("/example")
    fun exampleMethod(
        @RequestHeader("x-custom-name", required = true) name: String,
        @RequestHeader("x-custom-email", required = true) age: String
    ): ResponseEntity<ApiClientResponseDTO> {
        val apiClientResponseDTO = apiUseCase.execute()
        return ResponseEntity.status(HttpStatus.OK).body(apiClientResponseDTO)
    }
}