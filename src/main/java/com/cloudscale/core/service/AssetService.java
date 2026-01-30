package com.cloudscale.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cloudscale.core.dto.AssetPerformanceDTO;
import com.cloudscale.core.dto.MarketDataDTO;
import com.cloudscale.core.dto.PortfolioSummaryDTO;
import com.cloudscale.core.model.Asset;
import com.cloudscale.core.repository.AssetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final MarketDataService marketDataService;

    /**
     * Records a new asset purchase to the database.
     */
    public Asset saveAsset(Asset asset) {
        log.info("Saving new asset record for: {}", asset.getSymbol());
        return assetRepository.save(asset);
    }

    /**
     * Retrieves all assets owned by the user.
     */
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    /**
     * Calculates the real-time performance of the entire portfolio.
     * This is the heart of the dashboard logic.
     */
    public PortfolioSummaryDTO getPortfolioSummary() {
        // Step 1: Fetch all user assets from the database
        List<Asset> assets = assetRepository.findAll();
        
        if (assets.isEmpty()) {
            return PortfolioSummaryDTO.builder()
                    .totalValue(0.0).totalCost(0.0).totalProfitLoss(0.0).profitPercentage(0.0)
                    .assets(List.of()).build();
        }

        // Step 2: Map each Asset to an AssetPerformanceDTO with real-time data
        List<AssetPerformanceDTO> performanceList = assets.stream()
            .map(this::calculateIndividualPerformance)
            .collect(Collectors.toList());

        // Step 3: Aggregate totals
        double totalMarketValue = performanceList.stream()
                .mapToDouble(AssetPerformanceDTO::getMarketValue).sum();
        double totalCost = performanceList.stream()
                .mapToDouble(p -> p.getAveragePrice() * p.getQuantity()).sum();

        // Step 4: Build final summary response
        double totalPL = totalMarketValue - totalCost;
        double plPercentage = (totalCost > 0) ? (totalPL / totalCost) * 100 : 0.0;

        return PortfolioSummaryDTO.builder()
                .totalValue(totalMarketValue)
                .totalCost(totalCost)
                .totalProfitLoss(totalPL)
                .profitPercentage(plPercentage)
                .assets(performanceList)
                .build();
    }
    
    /**
     * Helper method to merge DB record with Real-time API data.
     */
    private AssetPerformanceDTO calculateIndividualPerformance(Asset asset) {
        // Call the external Alpha Vantage API
        MarketDataDTO liveData = marketDataService.getEtfPrice(asset.getSymbol());
        
        // Fallback to average price if API fails
        double currentPrice = (liveData != null) ? liveData.getPrice() : asset.getAveragePrice();

        AssetPerformanceDTO dto = new AssetPerformanceDTO();
        dto.setSymbol(asset.getSymbol());
        dto.setQuantity(asset.getQuantity());
        dto.setAveragePrice(asset.getAveragePrice());
        dto.setCurrentPrice(currentPrice);
        
        // Financial formulas
        double marketValue = currentPrice * asset.getQuantity();
        double costBasis = asset.getAveragePrice() * asset.getQuantity();
        
        dto.setMarketValue(marketValue);
        dto.setProfitLoss(marketValue - costBasis);
        
        return dto;
    }
}