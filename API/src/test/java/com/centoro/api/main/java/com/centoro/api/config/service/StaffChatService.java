package com.centoro.api.service;

import com.centoro.api.entities.StaffChat;
import com.centoro.api.entities.StaffChatMessage;
import com.centoro.api.repositories.StaffChatRepository;
import com.centoro.api.repositories.StaffChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StaffChatService {
    
    @Autowired
    private StaffChatRepository staffChatRepository;
    
    @Autowired
    private StaffChatMessageRepository messageRepository;
    
    @Transactional
    public StaffChat createChat(StaffChat chat) {
        chat.setChatId("SCH-" + String.format("%04d", System.currentTimeMillis() % 10000));
        chat.setCreatedAt(new Date());
        chat.setUpdatedAt(new Date());
        if (chat.getType() == null) chat.setType("team");
        return staffChatRepository.save(chat);
    }
    
    @Transactional
    public StaffChatMessage addMessage(StaffChatMessage message) {
        message.setMessageId("SMS-" + String.format("%04d", System.currentTimeMillis() % 10000));
        message.setCreatedAt(new Date());
        if (message.getSystem() == null) message.setSystem(false);
        return messageRepository.save(message);
    }
    
    public List<StaffChat> getAllChats() {
        return staffChatRepository.findAll();
    }
    
    public List<StaffChat> getChatsByType(String type) {
        return staffChatRepository.findByType(type);
    }
    
    public List<StaffChat> getChatsByProject(Long projectId) {
        return staffChatRepository.findByProjectId(projectId);
    }
    
    public List<StaffChat> getTodaysChats() {
        return staffChatRepository.findTodaysChats();
    }
    
    public Optional<StaffChat> getChatById(Long id) {
        return staffChatRepository.findById(id);
    }
    
    public Optional<StaffChat> getChatByChatId(String chatId) {
        return staffChatRepository.findByChatId(chatId);
    }
    
    public List<StaffChatMessage> getMessages(Long chatId) {
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }
    
    @Transactional
    public void deleteChat(Long id) {
        staffChatRepository.deleteById(id);
    }
}