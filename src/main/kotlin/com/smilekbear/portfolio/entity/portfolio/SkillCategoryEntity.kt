package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "skill_categories")
class SkillCategoryEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(name = "icon_key",nullable = false)
    val iconKey: String = "",

    @Column(name = "sort_order")
    val sortOrder : Int ? = null
)