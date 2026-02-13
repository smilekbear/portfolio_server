package com.smilekbear.portfolio.entity.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "projects")
class ProjectItemEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = 0,

    @Column(name = "full_title")
    val fullTitle: String? = null,

    @Column
    val title: String = "",

    @Column( nullable = false)
    val category: String = "",

    @Column(name = "project_info")
    val projectInfo: String = "",

    @Column(name = "date_range")
    val dateRange: String = "",

    @Column(name = "thumbnail_url")
    val thumbnailUrl: String? = null,

    @Column()
    val votes: Int? = 0,

    @Column()
    val review: String? = null,

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    val skills: List<ProjectSkillEntity> = emptyList(),
)