package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "careers")
class CareerEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var period: String = "",

    @Column(nullable = false)
    val title: String = "",

    @Column(name = "sub_title")
    val subTitle: String? = "",

    @Column(name = "sort_order")
    val sortOrder: Int? = null,

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    val works: Set<CareerWorkEntity> = emptySet(),

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    val skills: Set<CareerSkillEntity> = emptySet(),
)