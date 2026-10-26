package com.centoro.api.service;

import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TimeZoneService {
    
    private static final List<String> TIME_ZONES = Arrays.asList(
        "Africa/Johannesburg",
        "Africa/Cairo",
        "Africa/Lagos",
        "Africa/Nairobi",
        "Europe/London",
        "Europe/Paris",
        "Europe/Berlin",
        "Europe/Rome",
        "Europe/Madrid",
        "Europe/Amsterdam",
        "Europe/Istanbul",
        "Europe/Moscow",
        "America/New_York",
        "America/Los_Angeles",
        "America/Chicago",
        "America/Denver",
        "America/Toronto",
        "America/Sao_Paulo",
        "America/Mexico_City",
        "Asia/Dubai",
        "Asia/Tokyo",
        "Asia/Shanghai",
        "Asia/Singapore",
        "Asia/Hong_Kong",
        "Asia/Kolkata",
        "Asia/Bangkok",
        "Asia/Seoul",
        "Australia/Sydney",
        "Australia/Melbourne",
        "Australia/Perth",
        "Pacific/Auckland",
        "Pacific/Honolulu"
    );
    
    public List<Map<String, String>> getAllTimeZones() {
        List<Map<String, String>> result = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        
        for (String zoneId : TIME_ZONES) {
            try {
                ZoneId zone = ZoneId.of(zoneId);
                ZonedDateTime now = ZonedDateTime.now(zone);
                
                Map<String, String> entry = new LinkedHashMap<>();
                entry.put("zone", zoneId);
                entry.put("time", now.format(timeFormatter));
                entry.put("date", now.format(dateFormatter));
                entry.put("full", now.format(fullFormatter));
                entry.put("offset", now.getOffset().toString());
                entry.put("city", getCityName(zoneId));
                
                result.add(entry);
            } catch (Exception e) {
                System.err.println("⚠️ Invalid timezone: " + zoneId);
            }
        }
        return result;
    }
    
    public Map<String, String> getTimeZone(String zoneId) {
        try {
            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime now = ZonedDateTime.now(zone);
            
            Map<String, String> entry = new LinkedHashMap<>();
            entry.put("zone", zoneId);
            entry.put("time", now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            entry.put("date", now.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
            entry.put("full", now.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
            entry.put("offset", now.getOffset().toString());
            entry.put("city", getCityName(zoneId));
            
            return entry;
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid timezone: " + zoneId);
            return error;
        }
    }
    
    private String getCityName(String zoneId) {
        Map<String, String> cityMap = new HashMap<>();
        cityMap.put("Africa/Johannesburg", "Johannesburg");
        cityMap.put("Africa/Cairo", "Cairo");
        cityMap.put("Africa/Lagos", "Lagos");
        cityMap.put("Africa/Nairobi", "Nairobi");
        cityMap.put("Europe/London", "London");
        cityMap.put("Europe/Paris", "Paris");
        cityMap.put("Europe/Berlin", "Berlin");
        cityMap.put("Europe/Rome", "Rome");
        cityMap.put("Europe/Madrid", "Madrid");
        cityMap.put("Europe/Amsterdam", "Amsterdam");
        cityMap.put("Europe/Istanbul", "Istanbul");
        cityMap.put("Europe/Moscow", "Moscow");
        cityMap.put("America/New_York", "New York");
        cityMap.put("America/Los_Angeles", "Los Angeles");
        cityMap.put("America/Chicago", "Chicago");
        cityMap.put("America/Denver", "Denver");
        cityMap.put("America/Toronto", "Toronto");
        cityMap.put("America/Sao_Paulo", "Sao Paulo");
        cityMap.put("America/Mexico_City", "Mexico City");
        cityMap.put("Asia/Dubai", "Dubai");
        cityMap.put("Asia/Tokyo", "Tokyo");
        cityMap.put("Asia/Shanghai", "Shanghai");
        cityMap.put("Asia/Singapore", "Singapore");
        cityMap.put("Asia/Hong_Kong", "Hong Kong");
        cityMap.put("Asia/Kolkata", "Kolkata");
        cityMap.put("Asia/Bangkok", "Bangkok");
        cityMap.put("Asia/Seoul", "Seoul");
        cityMap.put("Australia/Sydney", "Sydney");
        cityMap.put("Australia/Melbourne", "Melbourne");
        cityMap.put("Australia/Perth", "Perth");
        cityMap.put("Pacific/Auckland", "Auckland");
        cityMap.put("Pacific/Honolulu", "Honolulu");
        return cityMap.getOrDefault(zoneId, zoneId);
    }
}