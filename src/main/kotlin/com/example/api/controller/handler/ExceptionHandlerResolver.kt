package com.example.api.controller.handler

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
class ExceptionHandlerResolver(
    private val applicationContext: ApplicationContext
) {

    fun findHandler(exception: Exception): ExceptionHandlerWrapper? {
        val beans = applicationContext.getBeansWithAnnotation(ControllerAdvice::class.java)
        for (bean in beans.values) {
            val methods = bean::class.java.methods.filter { it.isAnnotationPresent(ExceptionHandler::class.java) }
            for (method in methods) {
                val handledException = method.getAnnotation(ExceptionHandler::class.java).value
                if (handledException.contains(exception::class)) {
                    return ExceptionHandlerWrapper(bean, method)
                }
            }
        }

        return null
    }
}

data class ExceptionHandlerWrapper(
    val bean: Any,
    val method: java.lang.reflect.Method
) {
    fun handleException(exception: Exception): Any? {
        return method.invoke(bean, exception)
    }
}