package com.smilekbear.portfolio.repository.portfolio

import com.smilekbear.portfolio.entity.portfolio.ProjectItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProjectItemRepository : JpaRepository<ProjectItemEntity, Long> {

    @Query("""
        select distinct p
        from ProjectItemEntity p
        left join fetch p.skills s
        order by p.id asc
    """)
    fun findAllWithSkills(): List<ProjectItemEntity>
}