package com.centoro.api.repositories;

import com.centoro.api.entities.StaffChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface StaffChatRepository extends JpaRepository<StaffChat, Long> {
    Optional<StaffChat> findByChatId(String chatId);
    List<StaffChat> findByType(String type);
    List<StaffChat> findByProjectId(Long projectId);
    List<StaffChat> findByTeamId(Long teamId);
    List<StaffChat> findByCalendarDate(java.util.Date calendarDate);
    
    @Query("SELECT c FROM StaffChat c WHERE c.calendarDate = CURRENT_DATE")
    List<StaffChat> findTodaysChats();
}