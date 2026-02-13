package com.smilekbear.portfolio.dto

data class PresignResponse (
    val key: String,
    val uploadUrl: String,
    val publicUrl: String,
    val method: String,
    val contentType: String,
    val expiresInSeconds: Long,
)