package com.smilekbear.portfolio.repository.portfolio

import com.smilekbear.portfolio.entity.portfolio.CareerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CareerRepository: JpaRepository<CareerEntity, Long> {
    @Query("""
        select distinct c
        from CareerEntity c
        left join fetch c.works w
        left join fetch c.skills s
        order by c.sortOrder asc nulls last, c.id asc
    """)
    fun findAllWithWorksAndSkills(): List<CareerEntity>
}