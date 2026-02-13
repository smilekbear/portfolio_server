package com.smilekbear.portfolio.dto

data class KnowledgeCreateRequest (
    val categoryId : Long,
    val title : String,
    val summary : String? = null,
    val content : String,
    val imageUrlList : Array<String> = emptyArray(),
    val status : String? = "DRAFT",
    )