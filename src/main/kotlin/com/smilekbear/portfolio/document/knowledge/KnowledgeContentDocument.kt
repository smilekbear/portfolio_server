package com.smilekbear.portfolio.document.knowledge

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "knowledge_contents")
data class KnowledgeContentDocument(
    @Id
    val id: String,
    val title : String,
    val category : String,
    val contentHtml : String,
    val createdAt : Instant = Instant.now(),
    val updatedAt : Instant = Instant.now(),
)