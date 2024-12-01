package com.example.api.aspect

import com.example.api.client.LogClient
import com.example.api.client.context.ClientContext
import com.example.api.client.dto.LogClientRequestDTO
import com.example.api.controller.handler.ExceptionHandlerResolver
import com.example.api.service.LogService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import kotlin.reflect.full.memberProperties

@Aspect
@Component
class LogAspect(
    private val logService: LogService,
    private val clientContext: ClientContext,
    private val exceptionHandlerResolver: ExceptionHandlerResolver,
    private val objectMapper: ObjectMapper,
    private val request: HttpServletRequest
) {

    @Pointcut("execution(* com.example.api.controller.ApiController.*(..))")
    fun controllerMethods() {}

    @Around("controllerMethods()")
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
        val path = request.requestURI
        val method = request.method
        val headers = request.headerNames.toList().associateWith { request.getHeader(it) }
        val queryParams = request.parameterMap.mapValues { it.value.joinToString(",") }

        val args = joinPoint.args
        val requestBody = args.find { it !is HttpServletRequest }
        val requestBodyJson = objectMapper.writeValueAsString(requestBody)

        var response: Any? = null
        var responseJson: String? = null
        var errorResponseJson: String? = null
        var exceptionMessage: String? = null

        try {
            response = joinPoint.proceed()
            responseJson = objectMapper.writeValueAsString(response)
        } catch (ex: Exception) {
            exceptionMessage = ex.message
            val errorResponse = invokeExceptionHandler(ex)
            errorResponseJson = objectMapper.writeValueAsString(errorResponse)

            throw ex
        } finally {
            val clientContextData = clientContext.getData()
            val logClientRequestDTO = LogClientRequestDTO(
                name = getProperty(clientContextData, "name").toString(),
                path = path,
                method = method,
                headers = headers,
                queryParams = queryParams,
                requestBody = requestBodyJson,
                responseBody = responseJson ?: errorResponseJson,
                exceptionMessage = exceptionMessage

            )

            logService.postLog(logClientRequestDTO)

            clientContext.clear()
        }

        return response
    }

    private fun invokeExceptionHandler(exception: Exception): Any? {
        val handler = exceptionHandlerResolver.findHandler(exception)
        return handler?.handleException(exception)
    }

    private fun getProperty(obj: Any?, propertyName: String): Any? {
        obj?.let {
            val kClass = obj::class
            val property = kClass.memberProperties.firstOrNull { it.name == propertyName }
            return property?.getter?.call(obj)
        }
        return null
    }
}