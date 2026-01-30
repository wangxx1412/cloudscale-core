package com.cloudscale.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudscale.core.dto.PortfolioSummaryDTO;
import com.cloudscale.core.service.AssetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final AssetService assetService;

    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummaryDTO> getSummary() {
        return ResponseEntity.ok(assetService.getPortfolioSummary());
    }
}