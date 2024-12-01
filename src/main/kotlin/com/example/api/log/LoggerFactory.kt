package com.example.api.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LoggerFactory {
    fun <T> getLogger(forClass: Class<T>): Logger = LoggerFactory.getLogger(forClass)
}