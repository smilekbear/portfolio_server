package com.smilekbear.portfolio.repository.knowledge

import com.smilekbear.portfolio.document.knowledge.KnowledgeContentDocument
import com.smilekbear.portfolio.entity.knowledge.KnowledgeArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.mongodb.repository.MongoRepository

interface KnowledgeRepository: JpaRepository<KnowledgeArticleEntity, Long> {
    @Query("""
    select a
    from KnowledgeArticleEntity a
    join fetch a.category c
    where a.status = com.smilekbear.portfolio.entity.knowledge.KnowledgeArticleStatus.PUBLISHED
    order by a.updatedAt desc
""")
    fun findPublishedWithCategory(): List<KnowledgeArticleEntity>
}

interface KnowledgeContentRepository : MongoRepository<KnowledgeContentDocument, String>