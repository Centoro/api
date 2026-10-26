package com.centoro.api.service;

import com.centoro.api.entities.Inventory;
import com.centoro.api.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Transactional
    public Inventory createItem(Inventory item) {
        item.setItemId("INV-" + String.format("%04d", System.currentTimeMillis() % 10000));
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        if (item.getQty() == null) item.setQty(0);
        if (item.getCost() == null) item.setCost(0.0);
        if (item.getPrice() == null) item.setPrice(0.0);
        if (item.getReorderLevel() == null) item.setReorderLevel(0);
        return inventoryRepository.save(item);
    }
    
    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }
    
    public List<Inventory> getItemsByCategory(String category) {
        return inventoryRepository.findByCategory(category);
    }
    
    public Optional<Inventory> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }
    
    public Optional<Inventory> getItemBySku(String sku) {
        return inventoryRepository.findBySku(sku);
    }
    
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByQtyLessThanEqual(0);
    }
    
    public List<Inventory> getItemsNeedingReorder() {
        return inventoryRepository.findByQtyLessThanEqual(10);
    }
    
    @Transactional
    public Inventory updateItem(Long id, Inventory item) {
        Inventory existing = inventoryRepository.findById(id).orElseThrow();
        existing.setName(item.getName());
        existing.setSku(item.getSku());
        existing.setCategory(item.getCategory());
        existing.setQty(item.getQty());
        existing.setCost(item.getCost());
        existing.setPrice(item.getPrice());
        existing.setReorderLevel(item.getReorderLevel());
        existing.setUpdatedAt(new Date());
        return inventoryRepository.save(existing);
    }
    
    @Transactional
    public Inventory updateStock(Long id, Integer qtyChange) {
        Inventory item = inventoryRepository.findById(id).orElseThrow();
        item.setQty(item.getQty() + qtyChange);
        if (item.getQty() < 0) item.setQty(0);
        item.setUpdatedAt(new Date());
        return inventoryRepository.save(item);
    }
    
    @Transactional
    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}