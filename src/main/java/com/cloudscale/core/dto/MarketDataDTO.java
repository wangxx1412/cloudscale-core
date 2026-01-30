package com.cloudscale.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MarketDataDTO {
    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("05. price")
    private Double price;

    @JsonProperty("09. change")
    private Double change;

    @JsonProperty("10. change percent")
    private String changePercent;
}