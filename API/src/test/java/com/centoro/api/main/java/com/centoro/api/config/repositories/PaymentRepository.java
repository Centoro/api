package com.centoro.api.repositories;

import com.centoro.api.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByPaymentId(String paymentId);
    List<Payment> findByInvoiceId(Long invoiceId);
    List<Payment> findByDateBetween(java.util.Date start, java.util.Date end);
    List<Payment> findBySource(String source);
}