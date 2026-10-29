package com.centoro.api.repositories;

import com.centoro.api.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    
    List<Invoice> findByClientId(String clientId);
    
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);
    
    Optional<Invoice> findByTrackingNumber(String trackingNumber);
    
    @Query("SELECT i FROM Invoice i WHERE i.status != 'paid' AND i.dueDate < CURRENT_DATE")
    List<Invoice> findOverdueInvoices();
}
