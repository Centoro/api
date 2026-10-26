package com.centoro.api.repositories;

import com.centoro.api.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    Optional<Asset> findByAssetId(String assetId);
    List<Asset> findByCategory(String category);
    List<Asset> findByStatus(String status);
    List<Asset> findByNameContainingIgnoreCase(String name);
}