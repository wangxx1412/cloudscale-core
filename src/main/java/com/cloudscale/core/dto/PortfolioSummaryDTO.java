package com.cloudscale.core.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder // Provides a clean way to create objects
public class PortfolioSummaryDTO {
    private Double totalValue;       // Total current market value
    private Double totalCost;        // Total money invested
    private Double totalProfitLoss;  // Net gain/loss
    private Double profitPercentage; // (Total Value / Total Cost) - 1
    private List<AssetPerformanceDTO> assets; // Individual performance
}