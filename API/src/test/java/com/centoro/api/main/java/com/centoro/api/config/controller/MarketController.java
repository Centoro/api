package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.service.MarketDataService;
import java.util.*;

@RestController
@RequestMapping("/api/markets")
@CrossOrigin(origins = "*")
public class MarketController {
    
    private final MarketDataService marketDataService;
    
    // No @Autowired needed - single constructor!
    public MarketController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }
    
    @GetMapping("/data")
    public ResponseEntity<?> getMarketData() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        
        Map<String, Object> data = new HashMap<>();
        data.put("markets", marketDataService.getMarketData());
        data.put("remainingRequests", marketDataService.getRemainingRequests());
        data.put("lastUpdated", marketDataService.getLastUpdated());
        
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}