package com.centoro.api.service;

import com.centoro.api.entities.Chat;
import com.centoro.api.entities.ChatMessage;
import com.centoro.api.repositories.ChatRepository;
import com.centoro.api.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    
    private final ChatRepository chatRepository;
    private final ChatMessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository, ChatMessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }
    
    @Transactional
    public Chat createChat(Chat chat) {
        chat.setChatId("CHT-" + String.format("%04d", System.currentTimeMillis() % 10000));
        chat.setCreatedAt(new Date());
        chat.setUpdatedAt(new Date());
        if (chat.getStatus() == null) chat.setStatus("open");
        if (chat.getPriority() == null) chat.setPriority("medium");
        return chatRepository.save(chat);
    }
    
    @Transactional
    public ChatMessage addMessage(ChatMessage message) {
        message.setMessageId("MSG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        message.setCreatedAt(new Date());
        return messageRepository.save(message);
    }
    
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }
    
    public List<Chat> getOpenChats() {
        return chatRepository.findOpenChats();
    }
    
    // FIX: Changed from Long to String
    public List<Chat> getChatsByClient(String clientId) {
        return chatRepository.findByClientId(clientId);
    }
    
    public Optional<Chat> getChatById(Long id) {
        return chatRepository.findById(id);
    }
    
    public Optional<Chat> getChatByChatId(String chatId) {
        return chatRepository.findByChatId(chatId);
    }
    
    public List<ChatMessage> getMessages(Long chatId) {
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }
    
    @Transactional
    public Chat updateChatStatus(Long id, String status) {
        Chat chat = chatRepository.findById(id).orElseThrow();
        chat.setStatus(status);
        chat.setUpdatedAt(new Date());
        return chatRepository.save(chat);
    }
    
    @Transactional
    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }
}
