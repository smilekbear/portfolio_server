package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "project_skills")
class ProjectSkillEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: ProjectItemEntity? = null,

    @Column(name = "skill_name", nullable = false)
    val skillName: String = "",
)