package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "skill_items")
class SkillItemEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: SkillCategoryEntity,

    @Column(nullable = false)
    val name: String,

    @Column(name = "sort_order")
    val sortOrder: Int? = null
)