package com.cloudscale.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MarketDataResponse {
    @JsonProperty("Global Quote")
    private MarketDataDTO globalQuote;
}