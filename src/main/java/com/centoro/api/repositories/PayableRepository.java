package com.centoro.api.repositories;

import com.centoro.api.entities.Payable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayableRepository extends JpaRepository<Payable, Long> {
    
    Optional<Payable> findByPayableId(String payableId);
    List<Payable> findByStatus(String status);
    List<Payable> findBySupplierContainingIgnoreCase(String supplier);
}