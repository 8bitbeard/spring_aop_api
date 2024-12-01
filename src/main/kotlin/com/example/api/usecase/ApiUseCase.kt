package com.example.api.usecase

import com.example.api.client.dto.ApiClientResponseDTO
import com.example.api.service.ApiService
import org.springframework.stereotype.Component

@Component
class ApiUseCase(
    private val apiService: ApiService
) {
    fun execute(): ApiClientResponseDTO {
        return apiService.fetchData()
    }
}