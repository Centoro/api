package com.centoro.api.repositories;

import com.centoro.api.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findByMessageId(String messageId);
    List<ChatMessage> findByChatId(Long chatId);
    List<ChatMessage> findByChatIdOrderByCreatedAtAsc(Long chatId);
    List<ChatMessage> findByStaffId(Long staffId);
}