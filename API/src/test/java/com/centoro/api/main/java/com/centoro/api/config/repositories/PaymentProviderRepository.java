package com.centoro.api.repositories;

import com.centoro.api.entities.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long> {
    List<PaymentProvider> findByStatus(String status);
}