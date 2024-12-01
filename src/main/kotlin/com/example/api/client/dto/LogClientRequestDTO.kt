package com.example.api.client.dto

data class LogClientRequestDTO(
    val name: String,
    val path: String,
    val method: String,
    val headers: Map<String, String>,
    val queryParams: Map<String, String>,
    val requestBody: String,
    val responseBody: String?,
    val exceptionMessage: String?
)
