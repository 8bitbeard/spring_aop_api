package com.example.api.client.context

import org.springframework.stereotype.Component

@Component
class ClientContext {
    private val threadLocal = ThreadLocal<Any?>()

    fun setData(data: Any?) {
        threadLocal.set(data)
    }

    fun getData(): Any? {
        return threadLocal.get()
    }

    fun clear() {
        threadLocal.remove()
    }
}