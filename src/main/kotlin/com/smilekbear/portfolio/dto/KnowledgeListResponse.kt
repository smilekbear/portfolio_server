package com.smilekbear.portfolio.dto

import java.time.OffsetDateTime

data class KnowledgeListResponse(
    val items: List<KnowledgeListItemDto>
)

data class KnowledgeListItemDto(
    val id: Long,
    val category: String,
    val iconKey: String?,
    val title: String,
    val summary: String?,
    val thumbnailUrl: String?,
    val contentRef: String?,
    val updatedAt: OffsetDateTime
)