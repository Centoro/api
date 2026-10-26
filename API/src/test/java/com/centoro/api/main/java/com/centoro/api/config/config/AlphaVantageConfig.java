package com.centoro.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alpha.vantage")
public class AlphaVantageConfig {
    private String key;
    
    public String getKey() { 
        return key; 
    }
    
    public void setKey(String key) { 
        this.key = key; 
    }
}