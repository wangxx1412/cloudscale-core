package com.cloudscale.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudscale.core.dto.MarketDataDTO;
import com.cloudscale.core.service.MarketDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/market")
@Slf4j
public class MarketController {

    private final MarketDataService marketDataService;

    // Standard constructor injection
    public MarketController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    /**
     * Get the latest market quote for a specific ETF.
     * @param symbol Ticker symbol (e.g., VFV.TO, QQC.TO)
     * @return A structured DTO containing price and change data
     */
    @GetMapping("/quote/{symbol}")
    public ResponseEntity<MarketDataDTO> getQuote(@PathVariable String symbol) {
        log.info("Received request for market quote: {}", symbol);
        
        MarketDataDTO quote = marketDataService.getEtfPrice(symbol);
        
        if (quote == null) {
            log.warn("Market data not found for symbol: {}", symbol);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quote);
    }
}