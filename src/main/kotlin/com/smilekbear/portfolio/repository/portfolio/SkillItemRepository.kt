package com.smilekbear.portfolio.repository.portfolio

import com.smilekbear.portfolio.entity.portfolio.SkillItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SkillItemRepository: JpaRepository<SkillItemEntity, Long> {
    @Query(
        value = "select i from SkillItemEntity i " +
                "join fetch i.category c " +
                "order by " +
                "coalesce(c.sortOrder, 9999), " +
                "coalesce(i.sortOrder, 9999), " +
                "i.name"
    )
    fun findAllWithCategoryOrdered(): List<SkillItemEntity>
}