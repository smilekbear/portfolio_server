package com.smilekbear.portfolio.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val apiHandlerLogInterceptor: ApiHandlerLogInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(apiHandlerLogInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/swagger/**",
                "/v3/api-docs/**",
                "/actuator/**"
            )
    }
}