package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "about_items")
class AboutItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "icon_key", nullable = false)
    val iconKey: String = "",

    @Column(nullable = false)
    val value: String = "",

    @Column(name = "sort_order")
    val sortOrder: Int? = null,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime? = null,

    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
)