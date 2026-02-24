package com.smilekbear.portfolio.controller

import com.smilekbear.portfolio.dto.PortfolioResponse
import com.smilekbear.portfolio.dto.common.ApiResponse
import com.smilekbear.portfolio.service.PortfolioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/portfolio")
class PortfolioController(
    private val portfolioService: PortfolioService
) {

    @GetMapping
    fun getPortfolio(): ResponseEntity<ApiResponse<PortfolioResponse>> {

        return ResponseEntity.ok(ApiResponse.ok(portfolioService.getPortfolio()))
    }
}