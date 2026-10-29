package com.centoro.api.repositories;

import com.centoro.api.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(String chatId);
    
    @Query("SELECT c FROM Chat c WHERE c.status = 'open'")
    List<Chat> findOpenChats();
    
    // FIX: Changed from Long to String
    List<Chat> findByClientId(String clientId);
}
