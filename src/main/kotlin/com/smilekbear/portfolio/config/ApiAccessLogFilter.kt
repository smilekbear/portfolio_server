package com.smilekbear.portfolio.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ApiHandlerLogInterceptor : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(ApiHandlerLogInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            log.info("[HANDLER] {}#{}", handler.beanType.simpleName, handler.method.name)
        }
        return true
    }
}