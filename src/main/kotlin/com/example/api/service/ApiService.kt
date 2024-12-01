package com.example.api.service

import com.example.api.client.ApiClient
import com.example.api.client.context.ClientContext
import com.example.api.client.dto.ApiClientResponseDTO
import org.springframework.stereotype.Service

@Service
class ApiService(
    private val apiClient: ApiClient,
    private val clientContext: ClientContext
) {
    fun fetchData(): ApiClientResponseDTO {
        val response = apiClient.call()
        clientContext.setData(response)

        return response
    }
}