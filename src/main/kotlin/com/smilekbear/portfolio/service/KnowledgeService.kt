package com.smilekbear.portfolio.service

import com.smilekbear.portfolio.document.knowledge.KnowledgeContentDocument
import com.smilekbear.portfolio.dto.*
import com.smilekbear.portfolio.entity.knowledge.KnowledgeArticleEntity
import com.smilekbear.portfolio.entity.knowledge.KnowledgeArticleStatus
import com.smilekbear.portfolio.entity.knowledge.KnowledgeCategoryEntity
import com.smilekbear.portfolio.repository.knowledge.KnowledgeContentRepository
import com.smilekbear.portfolio.repository.knowledge.KnowledgeRepository
import jakarta.persistence.EntityManager
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.OffsetDateTime

@Service
class KnowledgeService (
    private val em: EntityManager,
    private val knowledgeRepository: KnowledgeRepository,
    private val knowledgeContentRepository: KnowledgeContentRepository,
){

    fun getKnowledgeList(
        keyword: String,
        limit: Int,
        offset: Int,
        category: String,
    ): Pair<KnowledgeListResponse, Int> {

        val kw  = keyword.trim()
        val filtered = knowledgeRepository.findPublishedWithCategory()
            .asSequence()
            .filter { a ->
                category.isBlank() ||
                        category.equals("ALL", ignoreCase = true) ||
                        a.category.name.equals(category, ignoreCase = true)
            }
            .filter { a ->
                if(kw.isBlank()) return@filter true
                (a.title ?: "").lowercase().contains(kw.lowercase())
            }
            .toList()

        val total = filtered.size

        val items = filtered
            .drop(offset.coerceAtLeast(0))
            .take(limit.coerceAtLeast(0))
            .map { a ->
                KnowledgeListItemDto(
                    id = a.id ?: 0L,
                    category = a.category.name,
                    iconKey = a.category.iconKey,
                    title = a.title,
                    summary = a.summary,
                    thumbnailUrl = a.thumbnailUrl,
                    contentRef =  a.contentRef,
                    updatedAt = a.updatedAt ?: a.createdAt ?: OffsetDateTime.now()
                )
            }

        return KnowledgeListResponse(items) to total
    }

    fun getDetail(id: String): KnowledgeDetailResponse {

        val doc = knowledgeContentRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Knowledge not found") }

        return KnowledgeDetailResponse(
            item = KnowledgeDetailDto(
                id = doc.id,
                title = doc.title,
                category = doc.category,
                contentHtml = doc.contentHtml,
                createdAt = doc.createdAt,
                updatedAt = doc.updatedAt
            )
        )
    }

    fun create(req: KnowledgeCreateRequest): Long {
        val categoryRef = em.getReference(KnowledgeCategoryEntity::class.java, req.categoryId)
        val contentRef = java.util.UUID.randomUUID().toString()
        val thumbnailUrl: String? = req.imageUrlList.firstOrNull()

        knowledgeContentRepository.save(
            KnowledgeContentDocument(
                id = contentRef,
                title = req.title,
                category = categoryRef.name,
                contentHtml = req.content
            )
        )
        val saved = knowledgeRepository.save(
            KnowledgeArticleEntity(
                category = categoryRef,
                title = req.title,
                summary = req.summary,
                thumbnailUrl = thumbnailUrl,
                contentRef = contentRef,
                status = KnowledgeArticleStatus.PUBLISHED,
            )
        )

        return saved.id ?: throw IllegalStateException("Failed to create knowledge article")
    }
}
