package com.centoro.api.service;

import com.centoro.api.entities.FridayDrop;
import com.centoro.api.repositories.FridayDropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FridayDropService {
    
    @Autowired
    private FridayDropRepository fridayDropRepository;
    
    @Transactional
    public FridayDrop createDrop(FridayDrop drop) {
        drop.setDropId("DRP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        drop.setCreatedAt(new Date());
        drop.setUpdatedAt(new Date());
        if (drop.getStatus() == null) drop.setStatus("live");
        if (drop.getClaimed() == null) drop.setClaimed(0);
        if (drop.getType() == null) drop.setType("audio");
        return fridayDropRepository.save(drop);
    }
    
    public List<FridayDrop> getAllDrops() {
        return fridayDropRepository.findAll();
    }
    
    public List<FridayDrop> getActiveDrops() {
        return fridayDropRepository.findActiveDrops();
    }
    
    public List<FridayDrop> getDropsByStatus(String status) {
        return fridayDropRepository.findByStatus(status);
    }
    
    public Optional<FridayDrop> getDropById(Long id) {
        return fridayDropRepository.findById(id);
    }
    
    @Transactional
    public FridayDrop updateDrop(Long id, FridayDrop drop) {
        FridayDrop existing = fridayDropRepository.findById(id).orElseThrow();
        existing.setTitle(drop.getTitle());
        existing.setType(drop.getType());
        existing.setGenre(drop.getGenre());
        existing.setDate(drop.getDate());
        existing.setExpiry(drop.getExpiry());
        existing.setBpm(drop.getBpm());
        existing.setDiscount(drop.getDiscount());
        existing.setPrice(drop.getPrice());
        existing.setSlots(drop.getSlots());
        existing.setClaimed(drop.getClaimed());
        existing.setCoverUrl(drop.getCoverUrl());
        existing.setAudioUrl(drop.getAudioUrl());
        existing.setStatus(drop.getStatus());
        existing.setUpdatedAt(new Date());
        return fridayDropRepository.save(existing);
    }
    
    @Transactional
    public FridayDrop incrementClaims(Long id) {
        FridayDrop drop = fridayDropRepository.findById(id).orElseThrow();
        drop.setClaimed(drop.getClaimed() + 1);
        if (drop.getSlots() != null && drop.getClaimed() >= drop.getSlots()) {
            drop.setStatus("expired");
        }
        drop.setUpdatedAt(new Date());
        return fridayDropRepository.save(drop);
    }
    
    @Transactional
    public void deleteDrop(Long id) {
        fridayDropRepository.deleteById(id);
    }
}