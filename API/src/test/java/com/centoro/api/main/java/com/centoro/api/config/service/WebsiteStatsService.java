package com.centoro.api.service;

import com.centoro.api.entities.WebsiteStats;
import com.centoro.api.repositories.WebsiteStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebsiteStatsService {
    
    @Autowired
    private WebsiteStatsRepository websiteStatsRepository;
    
    @Transactional
    public WebsiteStats createStats(WebsiteStats stats) {
        stats.setStatsId("WST-" + String.format("%04d", System.currentTimeMillis() % 10000));
        stats.setCreatedAt(new Date());
        stats.setUpdatedAt(new Date());
        if (stats.getVisitors() == null) stats.setVisitors(0);
        if (stats.getDownloads() == null) stats.setDownloads(0);
        if (stats.getConversionRate() == null) stats.setConversionRate(0.0);
        return websiteStatsRepository.save(stats);
    }
    
    public List<WebsiteStats> getAllStats() {
        return websiteStatsRepository.findAll();
    }
    
    public List<WebsiteStats> getStatsByDateRange(Date start, Date end) {
        return websiteStatsRepository.findByDateBetween(start, end);
    }
    
    public Optional<WebsiteStats> getLatestStats() {
        return websiteStatsRepository.findTopByOrderByDateDesc();
    }
    
    public Optional<WebsiteStats> getStatsById(Long id) {
        return websiteStatsRepository.findById(id);
    }
    
    public Long getTotalVisitors() {
        return websiteStatsRepository.getTotalVisitors();
    }
    
    public Long getTotalDownloads() {
        return websiteStatsRepository.getTotalDownloads();
    }
    
    @Transactional
    public WebsiteStats updateStats(Long id, WebsiteStats stats) {
        WebsiteStats existing = websiteStatsRepository.findById(id).orElseThrow();
        existing.setVisitors(stats.getVisitors());
        existing.setDownloads(stats.getDownloads());
        existing.setConversionRate(stats.getConversionRate());
        existing.setTopCountry(stats.getTopCountry());
        existing.setDate(stats.getDate());
        existing.setUpdatedAt(new Date());
        return websiteStatsRepository.save(existing);
    }
    
    @Transactional
    public WebsiteStats incrementVisitors(Long id) {
        WebsiteStats stats = websiteStatsRepository.findById(id).orElseThrow();
        stats.setVisitors(stats.getVisitors() + 1);
        stats.setUpdatedAt(new Date());
        return websiteStatsRepository.save(stats);
    }
    
    @Transactional
    public WebsiteStats incrementDownloads(Long id) {
        WebsiteStats stats = websiteStatsRepository.findById(id).orElseThrow();
        stats.setDownloads(stats.getDownloads() + 1);
        stats.setUpdatedAt(new Date());
        return websiteStatsRepository.save(stats);
    }
    
    @Transactional
    public void deleteStats(Long id) {
        websiteStatsRepository.deleteById(id);
    }
}