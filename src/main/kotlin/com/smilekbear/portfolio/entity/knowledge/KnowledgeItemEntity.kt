package com.smilekbear.portfolio.entity.knowledge

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

/**
 * RDBMS: knowledge_categories
 */
@Entity
@Table(name = "knowledge_categories")
class KnowledgeCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false, unique = true, length = 64)
    var name: String,

    // 프론트에서 아이콘 매핑용 key (예: ANDROID, REACT, SPRINGBOOT ...)
    @Column(name = "icon_key", nullable = true, length = 64)
    var iconKey: String? = null,

    @Column(name = "sort_order")
    var sortOrder: Int? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: OffsetDateTime? = null,
)

/**
 * RDBMS: knowledge_articles
 * (리스트에 필요한 메타 데이터만 RDBMS에 두고, 상세 content는 MongoDB에 둔다고 했으니
 *  여기에는 contentRef(=mongo document id/slug)만 저장)
 */
@Entity
@Table(name = "knowledge_articles")
class KnowledgeArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: KnowledgeCategoryEntity,

    @Column(name = "title", nullable = false, length = 200)
    var title: String,

    @Column(name = "summary", columnDefinition = "text")
    var summary: String? = null,

    @Column(name = "thumbnail_url", length = 500)
    var thumbnailUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    var status: KnowledgeArticleStatus = KnowledgeArticleStatus.DRAFT,

    // MongoDB 문서 참조값(문서 id or slug)
    @Column(name = "content_ref", nullable = false, length = 128)
    var contentRef: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: OffsetDateTime? = null,
)

enum class KnowledgeArticleStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED
}