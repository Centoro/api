package com.centoro.api.repositories;

import com.centoro.api.entities.SocialPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SocialPlatformRepository extends JpaRepository<SocialPlatform, Long> {
    List<SocialPlatform> findByStatus(String status);
}