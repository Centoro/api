package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.service.CurrencyService;
import com.centoro.api.service.TimeZoneService;
import java.util.*;

@RestController
@RequestMapping("/api/currency")
@CrossOrigin(origins = "*")
public class CurrencyController {
    
    private final CurrencyService currencyService;
    private final TimeZoneService timeZoneService;
    
    // No @Autowired needed - single constructor!
    public CurrencyController(CurrencyService currencyService, TimeZoneService timeZoneService) {
        this.currencyService = currencyService;
        this.timeZoneService = timeZoneService;
    }
    
    @GetMapping("/rates")
    public ResponseEntity<?> getRates() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        
        Map<String, Object> data = new HashMap<>();
        data.put("base", "ZAR");
        data.put("rates", currencyService.getLatestRates());
        data.put("lastUpdated", currencyService.getLastUpdated());
        
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/timezones")
    public ResponseEntity<?> getTimeZones() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", timeZoneService.getAllTimeZones());
        return ResponseEntity.ok(response);
    }
}