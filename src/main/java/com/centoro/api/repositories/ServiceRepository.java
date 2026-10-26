package com.centoro.api.repositories;

import com.centoro.api.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    
    Optional<Service> findByServiceId(String serviceId);
    List<Service> findByCategory(String category);
    List<Service> findByNameContainingIgnoreCase(String name);
    List<Service> findByType(String type);
}