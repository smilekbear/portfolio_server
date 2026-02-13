package com.smilekbear.portfolio.dto

import java.time.Instant
import java.time.OffsetDateTime

data class KnowledgeDetailResponse (
    val item : KnowledgeDetailDto
)

data class KnowledgeDetailDto(
    val id: String,
    val title : String,
    val category : String,
    val contentHtml : String,
    val createdAt : Instant,
    val updatedAt : Instant,
)
