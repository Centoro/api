package com.centoro.api.controller;

import com.centoro.api.dto.WebsiteChatDTO;
import com.centoro.api.dto.WebsiteChatSessionDTO;
import com.centoro.api.entities.WebsiteChat;
import com.centoro.api.entities.WebsiteChatSession;
import com.centoro.api.service.WebsiteChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/website/chat")
@CrossOrigin(origins = "*")
public class WebsiteChatController {

    @Autowired
    private WebsiteChatService websiteChatService;

    // ============ SESSIONS ============
    
    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions() {
        List<WebsiteChatSessionDTO> sessions = websiteChatService.getAllSessions().stream()
            .map(this::convertSessionToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", sessions
        ));
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        Optional<WebsiteChatSession> sessionOpt = websiteChatService.getSessionById(sessionId);
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteChatSession session = sessionOpt.get();
        WebsiteChatSessionDTO dto = convertSessionToDTO(session);
        dto.setMessages(websiteChatService.getMessagesBySession(sessionId).stream()
            .map(this::convertMessageToDTO)
            .collect(Collectors.toList()));
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dto
        ));
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody WebsiteChatSession session) {
        WebsiteChatSession saved = websiteChatService.createSession(session);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertSessionToDTO(saved),
            "message", "Chat session created"
        ));
    }

    @PutMapping("/sessions/{sessionId}/status")
    public ResponseEntity<?> updateSessionStatus(@PathVariable String sessionId, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Status is required"
            ));
        }
        
        try {
            WebsiteChatSession updated = websiteChatService.updateSessionStatus(sessionId, status);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertSessionToDTO(updated),
                "message", "Session status updated"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/sessions/{sessionId}/read")
    public ResponseEntity<?> markSessionRead(@PathVariable String sessionId) {
        try {
            websiteChatService.markSessionRead(sessionId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Session marked as read"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============ MESSAGES ============
    
    @GetMapping("/messages/{sessionId}")
    public ResponseEntity<?> getMessages(@PathVariable String sessionId) {
        List<WebsiteChat> messages = websiteChatService.getMessagesBySession(sessionId);
        List<WebsiteChatDTO> messageDTOs = messages.stream()
            .map(this::convertMessageToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", messageDTOs
        ));
    }

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody WebsiteChat message) {
        if (message.getSessionId() == null || message.getText() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Session ID and text are required"
            ));
        }
        
        // Ensure session exists
        Optional<WebsiteChatSession> sessionOpt = websiteChatService.getSessionById(message.getSessionId());
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Session not found"
            ));
        }
        
        if (message.getFrom() == null) message.setFrom("visitor");
        
        WebsiteChat saved = websiteChatService.addMessage(message);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertMessageToDTO(saved),
            "message", "Message sent"
        ));
    }

    private WebsiteChatSessionDTO convertSessionToDTO(WebsiteChatSession session) {
        WebsiteChatSessionDTO dto = new WebsiteChatSessionDTO();
        dto.setId(session.getId());
        dto.setSessionId(session.getSessionId());
        dto.setName(session.getName());
        dto.setContact(session.getContact());
        dto.setDepartment(session.getDepartment());
        dto.setService(session.getService());
        dto.setSource(session.getSource());
        dto.setStatus(session.getStatus());
        dto.setUnread(session.getUnread());
        dto.setCreatedAt(session.getCreatedAt());
        dto.setLastMessageAt(session.getLastMessageAt());
        return dto;
    }

    private WebsiteChatDTO convertMessageToDTO(WebsiteChat message) {
        WebsiteChatDTO dto = new WebsiteChatDTO();
        dto.setId(message.getId());
        dto.setChatMsgId(message.getChatMsgId());
        dto.setSessionId(message.getSessionId());
        dto.setFrom(message.getFrom());
        dto.setText(message.getText());
        dto.setDepartment(message.getDepartment());
        dto.setService(message.getService());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
}