package com.example.api.client

import com.example.api.client.dto.ApiClientResponseDTO
import org.springframework.stereotype.Component

@Component
class ApiClient {

    fun call(): ApiClientResponseDTO {
        throw Exception("Exception thrown on ApiClient")
        return ApiClientResponseDTO(
            name = "ExampleName",
            email = "Example@example.com",
            age = 33
        )
    }
}