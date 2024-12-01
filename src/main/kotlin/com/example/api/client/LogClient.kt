package com.example.api.client

import com.example.api.client.dto.LogClientRequestDTO
import com.example.api.log.LoggerFactory
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class LogClient(
    private val loggerFactory: LoggerFactory
) {
    private val logger: Logger = loggerFactory.getLogger(this::class.java)

    fun postLog(logClientRequestDTO: LogClientRequestDTO) {
        logger.info("[LogClient] Starting log call")
        Thread.sleep(2000)
        logger.info("[LogClient] Finishing log call")
    }
}