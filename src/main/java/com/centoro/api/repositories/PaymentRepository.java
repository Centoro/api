package com.centoro.api.repositories;

import com.centoro.api.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // FIX: invoiceId is String now
    List<Payment> findByInvoiceId(String invoiceId);
}
