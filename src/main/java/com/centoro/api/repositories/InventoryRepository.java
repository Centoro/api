package com.centoro.api.repositories;

import com.centoro.api.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByItemId(String itemId);
    Optional<Inventory> findBySku(String sku);
    List<Inventory> findByCategory(String category);
    List<Inventory> findByNameContainingIgnoreCase(String name);
    List<Inventory> findByQtyLessThanEqual(Integer reorderLevel);
}