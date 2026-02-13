package com.smilekbear.portfolio.controller

import com.smilekbear.portfolio.dto.KnowledgeCreateRequest
import com.smilekbear.portfolio.dto.KnowledgeDetailResponse
import com.smilekbear.portfolio.dto.KnowledgeListResponse
import com.smilekbear.portfolio.dto.common.ApiResponse
import com.smilekbear.portfolio.service.KnowledgeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(value = ["/api/knowledge"])
class KnowledgeController (
    val knowledgeService: KnowledgeService
){

    @GetMapping
    fun getKnowledge(
        keyword: String,
        limit: Int,
        offset: Int,
        category: String
    ): ResponseEntity<ApiResponse<KnowledgeListResponse>> {

        val (body, total) = knowledgeService.getKnowledgeList(keyword, limit, offset, category)

        return ResponseEntity.ok(
            ApiResponse.list(
                data = body,
                total = total,
                message = "SUCCESS"
            )
        )
    }

    @GetMapping("/{id}")
    fun getKnowledgeDetail(
        @PathVariable id: String
    ): ResponseEntity<ApiResponse<KnowledgeDetailResponse>> {
        val body = knowledgeService.getDetail(id)

        return ResponseEntity.ok(ApiResponse.ok(data = body))
    }

    @PostMapping
    fun createKnowledge(
        @RequestBody
        knowledgeCreateRequest: KnowledgeCreateRequest
    ): ResponseEntity<ApiResponse<Map<String, Long>>>{

        val id = knowledgeService.create(knowledgeCreateRequest)

        return ResponseEntity
            .created(URI.create("/api/knowledge/$id"))
            .body(ApiResponse.ok(mapOf("id" to id), message = "CREATED"))
    }
}