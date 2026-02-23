package com.smilekbear.portfolio.service


import com.smilekbear.portfolio.dto.*
import com.smilekbear.portfolio.repository.portfolio.ProjectItemRepository
import com.smilekbear.portfolio.repository.portfolio.AboutItemRepository
import com.smilekbear.portfolio.repository.portfolio.CareerRepository
import com.smilekbear.portfolio.repository.portfolio.SkillItemRepository
import org.springframework.stereotype.Service

@Service
class PortfolioService (
    private val skillItemRepository: SkillItemRepository,
    private val aboutItemRepository: AboutItemRepository,
    private val projectItemRepository: ProjectItemRepository,
    private val careerRepository: CareerRepository,
) {

    fun getPortfolio(): PortfolioResponse {
        val aboutItems = aboutItemRepository.findAllWithAboutOrdered()
        val skillItems = skillItemRepository.findAllWithCategoryOrdered()
        val projectItems = projectItemRepository.findAllWithSkills()
        val careerItems = careerRepository.findAllWithWorksAndSkills()
        val abouts : List<AboutItemDto> =
            aboutItems
                .map { item ->
                    AboutItemDto(
                        iconKey = item.iconKey,
                        value = item.value
                    )
                }

        val skills : List<SkillCategoryDto> =
            skillItems
            .groupBy { it.category.name }
                .map { (categoryName, items) ->
                    SkillCategoryDto(
                        iconKey = items.first().category.iconKey,
                        category = categoryName,
                        items = items.map { it.name }
                    )
                }

        val projects : List<ProjectDto> =
            projectItems.map { item ->
                ProjectDto(
                    title = item.title,
                    category =  item.category,
                    dateRange = item.dateRange,
                    thumbnailUrl = item.thumbnailUrl,
                    review = item.review,
                    skills = item.skills.map { it.skillName }
                )
            }

//        val projects: List<ProjectDto> =
//            projectItems.mapNotNull { item ->
//                if (item.fullTitle.isNullOrBlank()) return@mapNotNull null
//                println("DB fullTitle raw = [${item.fullTitle}] len=${item.fullTitle?.length}")
//
//                ProjectDto(
//                    title = item.title,
//                    category = item.category,
//                    dateRange = item.dateRange,
//                    thumbnailUrl = item.thumbnailUrl,
//                    review = item.review,
//                    skills = item.skills.map { it.skillName }
//                )
//            }

        val careers : List<CareerDto> =
            careerItems.map { c ->
                CareerDto(
                    period = c.period,
                    title = c.title,
                    subTitle = c.subTitle,
                    works = c.works.sortedBy { it.sortOrder ?: 9999 }.map { it.workText },
                    skills = c.skills.sortedBy { it.sortOrder ?: 9999 }.map { it.skillKey.name }
                )

            }
        return PortfolioResponse(
            about = abouts,
            skills = skills,
            projects = projects,
            careers = careers
        )
    }
}