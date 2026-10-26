package com.centoro.api.repositories;

import com.centoro.api.entities.WebsiteChat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WebsiteChatRepository extends JpaRepository<WebsiteChat, Long> {
    
    Optional<WebsiteChat> findByChatMsgId(String chatMsgId);
    List<WebsiteChat> findBySessionId(String sessionId);
    List<WebsiteChat> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}