package com.smilekbear.portfolio.entity.portfolio

import com.smilekbear.portfolio.enums.portfolio.CareerSkillKey
import jakarta.persistence.*

@Entity
@Table(name = "career_skills")
class CareerSkillEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    val career: CareerEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_key", nullable = false, length = 50)
    val skillKey: CareerSkillKey = CareerSkillKey.JAVA,

    @Column(name = "sort_order")
    val sortOrder: Int? = null,
)