package com.centoro.api.repositories;

import com.centoro.api.entities.WebsiteChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WebsiteChatSessionRepository extends JpaRepository<WebsiteChatSession, Long> {
    Optional<WebsiteChatSession> findBySessionId(String sessionId);
}