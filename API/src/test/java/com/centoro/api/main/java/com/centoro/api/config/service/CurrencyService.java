package com.centoro.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class CurrencyService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String FRANKFURTER_URL = "https://api.frankfurter.app";
    
    private Map<String, Double> latestRates = new HashMap<>();
    private LocalDateTime lastUpdated = null;
    
    @Scheduled(fixedRate = 3600000)
    @SuppressWarnings("unchecked")
    public void updateCurrencyRates() {
        try {
            String url = FRANKFURTER_URL + "/latest?from=ZAR";
            var response = restTemplate.getForObject(url, Map.class);
            
            if (response != null && response.containsKey("rates")) {
                Object ratesObj = response.get("rates");
                Map<String, Double> rates = objectMapper.convertValue(ratesObj, Map.class);
                latestRates = rates;
                lastUpdated = LocalDateTime.now();
                
                System.out.println("✅ Currency rates updated at: " + lastUpdated);
                System.out.println("📊 Rates: " + rates);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to update currency rates: " + e.getMessage());
        }
    }
    
    public Map<String, Double> getLatestRates() {
        if (latestRates.isEmpty()) {
            updateCurrencyRates();
        }
        return latestRates;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}
