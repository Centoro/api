package com.centoro.api.service;

import com.centoro.api.entities.Analytics;
import com.centoro.api.repositories.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsService {
    
    @Autowired
    private AnalyticsRepository analyticsRepository;
    
    @Transactional
    public Analytics createAnalytic(Analytics analytic) {
        analytic.setAnalyticsId("ANA-" + String.format("%04d", System.currentTimeMillis() % 10000));
        analytic.setCreatedAt(new Date());
        analytic.setUpdatedAt(new Date());
        if (analytic.getCount() == null) analytic.setCount(0);
        if (analytic.getInteractions() == null) analytic.setInteractions(0);
        return analyticsRepository.save(analytic);
    }
    
    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }
    
    public List<Analytics> getTopCountries() {
        return analyticsRepository.findTopCountries();
    }
    
    public List<Analytics> getRecentInteractions() {
        return analyticsRepository.findRecentInteractions();
    }
    
    public List<Analytics> getByType(String type) {
        return analyticsRepository.findByType(type);
    }
    
    public Optional<Analytics> getAnalyticById(Long id) {
        return analyticsRepository.findById(id);
    }
    
    @Transactional
    public Analytics incrementAnalytic(String type, String label) {
        // Find existing or create new
        List<Analytics> existing = analyticsRepository.findByType(type);
        Optional<Analytics> match = existing.stream()
            .filter(a -> label.equals(a.getLabel()))
            .findFirst();
        
        if (match.isPresent()) {
            Analytics analytic = match.get();
            analytic.setCount(analytic.getCount() + 1);
            analytic.setUpdatedAt(new Date());
            return analyticsRepository.save(analytic);
        } else {
            Analytics newAnalytic = new Analytics();
            newAnalytic.setType(type);
            newAnalytic.setLabel(label);
            newAnalytic.setCount(1);
            newAnalytic.setCreatedAt(new Date());
            newAnalytic.setUpdatedAt(new Date());
            return analyticsRepository.save(newAnalytic);
        }
    }
    
    @Transactional
    public void deleteAnalytic(Long id) {
        analyticsRepository.deleteById(id);
    }
}