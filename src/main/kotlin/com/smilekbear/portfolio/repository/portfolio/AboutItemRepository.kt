package com.smilekbear.portfolio.repository.portfolio

import com.smilekbear.portfolio.entity.portfolio.AboutItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AboutItemRepository: JpaRepository<AboutItemEntity,Long> {
    @Query(
        "select i from AboutItemEntity i " +
                "order by coalesce(i.sortOrder, 9999) asc"
    )
    fun findAllWithAboutOrdered() : List<AboutItemEntity>
}