package com.smilekbear.portfolio.dto.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val code : Int,
    val message : String,
    val data: T? = null,
    val total: Int? = null,
) {
    companion object {
        fun <T> ok(data: T, message: String = "OK") =
            ApiResponse(code = 0, message = message, data = data, total = null)

        fun ok(message: String = "OK") =
            ApiResponse(code = 0, message = message, data = null, total = null)

        fun <T> list(data: T, total: Int, message: String = "OK") =
            ApiResponse(code = 0, message = message, data = data, total = total)

        fun fail(code : Int, message: String = "OK", data : Any? = null) =
            ApiResponse(code = code, message = message, data = data)
    }
}