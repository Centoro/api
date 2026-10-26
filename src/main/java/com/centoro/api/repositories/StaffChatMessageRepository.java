package com.centoro.api.repositories;

import com.centoro.api.entities.StaffChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StaffChatMessageRepository extends JpaRepository<StaffChatMessage, Long> {
    Optional<StaffChatMessage> findByMessageId(String messageId);
    List<StaffChatMessage> findByChatId(Long chatId);
    List<StaffChatMessage> findByChatIdOrderByCreatedAtAsc(Long chatId);
    List<StaffChatMessage> findByStaffId(Long staffId);
}