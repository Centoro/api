package com.centoro.api.repositories;

import com.centoro.api.entities.WebsitePriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WebsitePriceListRepository extends JpaRepository<WebsitePriceList, Long> {
    
    Optional<WebsitePriceList> findByPriceListId(String priceListId);
    List<WebsitePriceList> findByType(String type);
}