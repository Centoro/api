package com.centoro.api.repositories;

import com.centoro.api.entities.EmailProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmailProviderRepository extends JpaRepository<EmailProvider, Long> {
    List<EmailProvider> findByStatus(String status);
}