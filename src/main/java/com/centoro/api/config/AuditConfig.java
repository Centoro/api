package com.centoro.api.config;

import com.centoro.api.entities.Staff;
import com.centoro.api.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    @Autowired
    private StaffRepository staffRepository;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("system");
            }
            
            String username = authentication.getName();
            
            Optional<Staff> staffOpt = staffRepository.findByUsername(username);
            if (staffOpt.isPresent()) {
                return Optional.of(staffOpt.get().getStaffId());
            }
            
            Optional<Staff> staffByEmail = staffRepository.findByEmail(username);
            if (staffByEmail.isPresent()) {
                return Optional.of(staffByEmail.get().getStaffId());
            }
            
            return Optional.of(username);
        }
    }
}