package com.example.api.service

import com.example.api.client.LogClient
import com.example.api.client.dto.LogClientRequestDTO
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class LogService(
    private val logClient: LogClient,
) {
    @Async
    fun postLog(logClientRequestDTO: LogClientRequestDTO) {
        logClient.postLog(logClientRequestDTO)
    }
}