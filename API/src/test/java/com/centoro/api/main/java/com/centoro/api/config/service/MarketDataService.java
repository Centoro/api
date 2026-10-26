package com.centoro.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class MarketDataService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Value("${alpha.vantage.key}")
    private String apiKey;
    
    private Map<String, Object> latestMarketData = new HashMap<>();
    private LocalDateTime lastUpdated = null;
    private int remainingRequests = 25;
    
    @PostConstruct
    public void init() {
        System.out.println("🔑 Alpha Vantage Key loaded: " + 
            (apiKey != null && !apiKey.isEmpty() && !apiKey.startsWith("${") ? 
            apiKey.substring(0, Math.min(4, apiKey.length())) + "****" : 
            "⚠️ KEY NOT SET!"));
        System.out.println("📊 Key length: " + (apiKey != null ? apiKey.length() : 0));
    }
    
    @Scheduled(cron = "0 0 8 * * *")
    @SuppressWarnings("unchecked")
    public void updateMarketData() {
        if (remainingRequests <= 0) {
            System.err.println("⚠️ Alpha Vantage daily limit reached! Next update tomorrow.");
            return;
        }
        
        try {
            if (apiKey == null || apiKey.isEmpty() || apiKey.startsWith("${")) {
                System.err.println("❌ Alpha Vantage API key is not set!");
                return;
            }
            
            String[] symbols = {"JSE:J200", "USDZAR=X", "EURUSD=X", "GBPUSD=X"};
            
            for (String symbol : symbols) {
                String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
                var response = restTemplate.getForObject(url, Map.class);
                
                if (response != null && response.containsKey("Global Quote")) {
                    Object quoteObj = response.get("Global Quote");
                    Map<String, String> quote = objectMapper.convertValue(quoteObj, Map.class);
                    latestMarketData.put(symbol, quote);
                    remainingRequests--;
                    System.out.println("✅ Updated: " + symbol);
                } else if (response != null && response.containsKey("Error Message")) {
                    System.err.println("❌ Alpha Vantage Error for " + symbol + ": " + response.get("Error Message"));
                }
            }
            
            lastUpdated = LocalDateTime.now();
            System.out.println("✅ Market data updated. Remaining requests: " + remainingRequests);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to update market data: " + e.getMessage());
        }
    }
    
    public Map<String, Object> getMarketData() {
        return latestMarketData;
    }
    
    public int getRemainingRequests() {
        return remainingRequests;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}