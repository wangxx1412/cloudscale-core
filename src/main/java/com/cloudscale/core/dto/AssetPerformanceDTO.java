package com.cloudscale.core.dto;

import lombok.Data;

@Data
public class AssetPerformanceDTO {
    private String symbol;
    private Double quantity;
    private Double currentPrice;
    private Double averagePrice;
    private Double marketValue;
    private Double profitLoss;
}