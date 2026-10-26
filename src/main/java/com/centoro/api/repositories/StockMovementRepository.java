package com.centoro.api.repositories;

import com.centoro.api.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    
    Optional<StockMovement> findByMovementId(String movementId);
    List<StockMovement> findByItemId(Long itemId);
    List<StockMovement> findByType(String type);
    List<StockMovement> findByItemIdOrderByDateDesc(Long itemId);
}