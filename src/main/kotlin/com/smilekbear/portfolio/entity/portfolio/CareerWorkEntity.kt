package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "career_works")
class CareerWorkEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    val career: CareerEntity? = null,

    @Column(name = "work_text", nullable = false, columnDefinition = "text")
    val workText: String = "",

    @Column(name = "sort_order")
    val sortOrder: Int? = null,
)