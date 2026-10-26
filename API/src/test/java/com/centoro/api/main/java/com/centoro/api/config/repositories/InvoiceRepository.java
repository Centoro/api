package com.centoro.api.repositories;

import com.centoro.api.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    Optional<Invoice> findByInvoiceId(String invoiceId);
    Optional<Invoice> findByTrackingNumber(String trackingNumber);
    
    List<Invoice> findByClientId(Long clientId);
    List<Invoice> findByProjectId(Long projectId);
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);
    List<Invoice> findByClientIdAndStatus(Long clientId, Invoice.InvoiceStatus status);
    
    @Query("SELECT i FROM Invoice i WHERE i.dueDate < CURRENT_DATE AND i.status != 'paid'")
    List<Invoice> findOverdueInvoices();
    
    @Query("SELECT i FROM Invoice i WHERE i.status = 'unpaid' AND i.dueDate < CURRENT_DATE")
    List<Invoice> findOverdueUnpaid();
    
    boolean existsByInvoiceId(String invoiceId);
    boolean existsByTrackingNumber(String trackingNumber);
}

