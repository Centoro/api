package com.centoro.api.service;

import com.centoro.api.entities.BinItem;
import com.centoro.api.repositories.BinRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BinService {
    
    @Autowired
    private BinRepository binRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Transactional
    public BinItem moveToBin(String collection, Long originalId, Object entity, String deletedBy) {
        BinItem binItem = new BinItem();
        binItem.setBinId("BIN-" + String.format("%04d", System.currentTimeMillis() % 10000));
        binItem.setCollection(collection);
        binItem.setOriginalId(originalId);
        binItem.setDeletedBy(deletedBy);
        binItem.setDeletedAt(new Date());
        binItem.setCreatedAt(new Date());
        
        try {
            String snapshot = objectMapper.writeValueAsString(entity);
            binItem.setSnapshot(snapshot);
        } catch (Exception e) {
            binItem.setSnapshot("{}");
        }
        
        return binRepository.save(binItem);
    }
    
    public List<BinItem> getAllBinItems() {
        return binRepository.findByOrderByDeletedAtDesc();
    }
    
    public List<BinItem> getBinItemsByCollection(String collection) {
        return binRepository.findByCollection(collection);
    }
    
    public Optional<BinItem> getBinItemById(Long id) {
        return binRepository.findById(id);
    }
    
    public Optional<BinItem> getBinItemByBinId(String binId) {
        return binRepository.findByBinId(binId);
    }
    
    @Transactional
    public void restoreBinItem(Long id) {
        binRepository.deleteById(id);
    }
    
    @Transactional
    public void restoreBinItemByOriginal(String collection, Long originalId) {
        binRepository.deleteByCollectionAndOriginalId(collection, originalId);
    }
    
    @Transactional
    public void emptyBin() {
        binRepository.deleteAll();
    }
    
    @Transactional
    public void deleteBinItem(Long id) {
        binRepository.deleteById(id);
    }
    
    public boolean isInBin(String collection, Long originalId) {
        return binRepository.findByCollection(collection).stream()
            .anyMatch(b -> b.getOriginalId().equals(originalId));
    }
}