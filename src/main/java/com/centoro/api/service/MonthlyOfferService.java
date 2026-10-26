package com.centoro.api.service;

import com.centoro.api.entities.MonthlyOffer;
import com.centoro.api.repositories.MonthlyOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyOfferService {
    
    @Autowired
    private MonthlyOfferRepository monthlyOfferRepository;
    
    @Transactional
    public MonthlyOffer createOffer(MonthlyOffer offer) {
        offer.setOfferId("MOF-" + String.format("%04d", System.currentTimeMillis() % 10000));
        offer.setCreatedAt(new Date());
        offer.setUpdatedAt(new Date());
        if (offer.getStatus() == null) offer.setStatus("live");
        if (offer.getClaimed() == null) offer.setClaimed(0);
        if (offer.getType() == null) offer.setType("service");
        return monthlyOfferRepository.save(offer);
    }
    
    public List<MonthlyOffer> getAllOffers() {
        return monthlyOfferRepository.findAll();
    }
    
    public List<MonthlyOffer> getActiveOffers() {
        return monthlyOfferRepository.findActiveOffers();
    }
    
    public List<MonthlyOffer> getOffersByStatus(String status) {
        return monthlyOfferRepository.findByStatus(status);
    }
    
    public Optional<MonthlyOffer> getOfferById(Long id) {
        return monthlyOfferRepository.findById(id);
    }
    
    @Transactional
    public MonthlyOffer updateOffer(Long id, MonthlyOffer offer) {
        MonthlyOffer existing = monthlyOfferRepository.findById(id).orElseThrow();
        existing.setTitle(offer.getTitle());
        existing.setType(offer.getType());
        existing.setGenre(offer.getGenre());
        existing.setMonth(offer.getMonth());
        existing.setDate(offer.getDate());
        existing.setExpiry(offer.getExpiry());
        existing.setBpm(offer.getBpm());
        existing.setDiscount(offer.getDiscount());
        existing.setPrice(offer.getPrice());
        existing.setSlots(offer.getSlots());
        existing.setClaimed(offer.getClaimed());
        existing.setCoverUrl(offer.getCoverUrl());
        existing.setAudioUrl(offer.getAudioUrl());
        existing.setStatus(offer.getStatus());
        existing.setUpdatedAt(new Date());
        return monthlyOfferRepository.save(existing);
    }
    
    @Transactional
    public MonthlyOffer incrementClaims(Long id) {
        MonthlyOffer offer = monthlyOfferRepository.findById(id).orElseThrow();
        offer.setClaimed(offer.getClaimed() + 1);
        if (offer.getSlots() != null && offer.getClaimed() >= offer.getSlots()) {
            offer.setStatus("expired");
        }
        offer.setUpdatedAt(new Date());
        return monthlyOfferRepository.save(offer);
    }
    
    @Transactional
    public void deleteOffer(Long id) {
        monthlyOfferRepository.deleteById(id);
    }
}