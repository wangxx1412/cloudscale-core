package com.cloudscale.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cloudscale.core.dto.MarketDataDTO;
import com.cloudscale.core.dto.MarketDataResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarketDataService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Injecting the API key from application.yml
    @Value("${app.api.key}")
    private String apiKey;

    /**
     * Fetches real-time ETF data from Alpha Vantage API.
     * @param symbol The ticker symbol (e.g., VFV.TO, QQC.TO)
     * @return JSON string containing market data
     */
    public MarketDataDTO getEtfPrice(String symbol) {
        String url = String.format(
            "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
            symbol, apiKey
        );

        try {
            log.info("Fetching real-time data for {}", symbol);
            MarketDataResponse response = restTemplate.getForObject(url, MarketDataResponse.class);
            
            if (response != null && response.getGlobalQuote() != null) {
                return response.getGlobalQuote();
            }
        } catch (RestClientException e) {
            log.error("Parsing error for symbol {}: {}", symbol, e.getMessage());
        }
        return null;
    }
}