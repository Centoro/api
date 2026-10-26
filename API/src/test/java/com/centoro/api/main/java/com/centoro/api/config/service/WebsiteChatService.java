package com.centoro.api.service;

import com.centoro.api.entities.WebsiteChat;
import com.centoro.api.entities.WebsiteChatSession;
import com.centoro.api.repositories.WebsiteChatRepository;
import com.centoro.api.repositories.WebsiteChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebsiteChatService {
    
    @Autowired
    private WebsiteChatRepository websiteChatRepository;
    
    @Autowired
    private WebsiteChatSessionRepository sessionRepository;
    
    @Transactional
    public WebsiteChatSession createSession(WebsiteChatSession session) {
        session.setSessionId("SES-" + System.currentTimeMillis());
        session.setCreatedAt(new Date());
        session.setLastMessageAt(new Date());
        if (session.getStatus() == null) session.setStatus("open");
        if (session.getUnread() == null) session.setUnread(0);
        return sessionRepository.save(session);
    }
    
    @Transactional
    public WebsiteChat addMessage(WebsiteChat message) {
        message.setChatMsgId("MSG-" + System.currentTimeMillis());
        message.setCreatedAt(new Date());
        
        // Update session last message time and unread count
        if (message.getSessionId() != null) {
            Optional<WebsiteChatSession> sessionOpt = sessionRepository.findBySessionId(message.getSessionId());
            if (sessionOpt.isPresent()) {
                WebsiteChatSession session = sessionOpt.get();
                session.setLastMessageAt(new Date());
                if ("visitor".equals(message.getFrom())) {
                    session.setUnread(session.getUnread() + 1);
                } else {
                    session.setUnread(0);
                }
                sessionRepository.save(session);
            }
        }
        
        return websiteChatRepository.save(message);
    }
    
    public List<WebsiteChat> getMessagesBySession(String sessionId) {
        return websiteChatRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }
    
    public Optional<WebsiteChatSession> getSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    public List<WebsiteChatSession> getAllSessions() {
        return sessionRepository.findAll();
    }
    
    @Transactional
    public WebsiteChatSession updateSessionStatus(String sessionId, String status) {
        WebsiteChatSession session = sessionRepository.findBySessionId(sessionId).orElseThrow();
        session.setStatus(status);
        return sessionRepository.save(session);
    }
    
    @Transactional
    public void markSessionRead(String sessionId) {
        WebsiteChatSession session = sessionRepository.findBySessionId(sessionId).orElseThrow();
        session.setUnread(0);
        sessionRepository.save(session);
    }
}