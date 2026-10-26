package com.centoro.api.repositories;

import com.centoro.api.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(String chatId);
    List<Chat> findByClientId(Long clientId);
    List<Chat> findByStatus(String status);
    List<Chat> findByPriority(String priority);
    List<Chat> findByClientIdAndStatus(Long clientId, String status);
    
    @Query("SELECT c FROM Chat c WHERE c.status = 'open' ORDER BY c.createdAt DESC")
    List<Chat> findOpenChats();
    
    @Query("SELECT c FROM Chat c WHERE c.calendarDate = CURRENT_DATE")
    List<Chat> findTodaysChats();
}