package com.smilekbear.portfolio.dto

data class PortfolioResponse(
    val about: List<AboutItemDto>,
    val skills: List<SkillCategoryDto>,
    val projects: List<ProjectDto>,
    val careers: List<CareerDto>
)

data class AboutItemDto(
    val iconKey: String,
    val value: String
)

data class SkillCategoryDto(
    val iconKey: String,
    val category: String,
    val items: List<String>
)

data class ProjectDto(
    val title: String,
    val category: String,
    val dateRange: String,
    val thumbnailUrl: String?,
    val skills: List<String>,
    val review: String?,
    val major: String?,
    val organization: String?
)

data class CareerDto(
    val period: String,
    val title: String,
    val subTitle: String?,
    val works: List<String>,
    val skills: List<String>
)