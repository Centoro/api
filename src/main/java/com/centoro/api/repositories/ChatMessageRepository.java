package com.centoro.api.repositories;

import com.centoro.api.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatIdOrderByCreatedAtAsc(Long chatId);
    List<ChatMessage> findByChatId(Long chatId);
}
