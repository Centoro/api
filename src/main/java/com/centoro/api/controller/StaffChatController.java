package com.centoro.api.controller;

import com.centoro.api.dto.StaffChatDTO;
import com.centoro.api.dto.StaffChatMessageDTO;
import com.centoro.api.entities.StaffChat;
import com.centoro.api.entities.StaffChatMessage;
import com.centoro.api.entities.Staff;
import com.centoro.api.repositories.StaffRepository;
import com.centoro.api.service.StaffChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff-chat")
@CrossOrigin(origins = "*")
public class StaffChatController {

    @Autowired
    private StaffChatService staffChatService;
    
    @Autowired
    private StaffRepository staffRepository;

    @GetMapping
    public ResponseEntity<?> getAllChats() {
        List<StaffChat> chats = staffChatService.getAllChats();
        List<StaffChatDTO> chatDTOs = chats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", chatDTOs
        ));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getChatsByType(@PathVariable String type) {
        List<StaffChat> chats = staffChatService.getChatsByType(type);
        List<StaffChatDTO> chatDTOs = chats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", chatDTOs
        ));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getChatsByProject(@PathVariable Long projectId) {
        List<StaffChat> chats = staffChatService.getChatsByProject(projectId);
        List<StaffChatDTO> chatDTOs = chats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", chatDTOs
        ));
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodaysChats() {
        List<StaffChat> chats = staffChatService.getTodaysChats();
        List<StaffChatDTO> chatDTOs = chats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", chatDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable Long id) {
        Optional<StaffChat> chatOpt = staffChatService.getChatById(id);
        if (chatOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        StaffChatDTO dto = convertToDTO(chatOpt.get());
        dto.setMessages(staffChatService.getMessages(id).stream()
            .map(this::convertMessageToDTO)
            .collect(Collectors.toList()));
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dto
        ));
    }

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody Map<String, Object> request) {
        String type = (String) request.get("type");
        String subject = (String) request.get("subject");
        Long projectId = request.get("projectId") != null ? 
            Long.valueOf(request.get("projectId").toString()) : null;
        Long teamId = request.get("teamId") != null ? 
            Long.valueOf(request.get("teamId").toString()) : null;
        String groupName = (String) request.get("groupName");
        String participants = (String) request.get("participants");
        String dateStr = (String) request.get("calendarDate");
        
        if (subject == null || subject.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Subject is required"
            ));
        }
        
        StaffChat chat = new StaffChat();
        chat.setType(type != null ? type : "team");
        chat.setSubject(subject);
        chat.setProjectId(projectId);
        chat.setTeamId(teamId);
        chat.setGroupName(groupName);
        chat.setParticipants(participants != null ? participants : "[]");
        if (dateStr != null && !dateStr.isEmpty()) {
            chat.setCalendarDate(java.sql.Date.valueOf(dateStr));
        }
        
        StaffChat saved = staffChatService.createChat(chat);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Staff chat created successfully"
        ));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<?> addMessage(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<StaffChat> chatOpt = staffChatService.getChatById(id);
        if (chatOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Long staffId = request.get("staffId") != null ? 
            Long.valueOf(request.get("staffId").toString()) : null;
        String text = (String) request.get("text");
        String attachment = (String) request.get("attachment");
        Boolean system = request.get("system") != null ? 
            Boolean.valueOf(request.get("system").toString()) : false;
        
        if (text == null || text.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Message text is required"
            ));
        }
        
        StaffChatMessage message = new StaffChatMessage();
        message.setChatId(id);
        message.setStaffId(staffId);
        message.setText(text);
        message.setAttachment(attachment);
        message.setSystem(system);
        
        StaffChatMessage saved = staffChatService.addMessage(message);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertMessageToDTO(saved),
            "message", "Message sent successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable Long id) {
        if (!staffChatService.getChatById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        staffChatService.deleteChat(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Staff chat deleted successfully"
        ));
    }

    private StaffChatDTO convertToDTO(StaffChat chat) {
        StaffChatDTO dto = new StaffChatDTO();
        dto.setId(chat.getId());
        dto.setChatId(chat.getChatId());
        dto.setType(chat.getType());
        dto.setSubject(chat.getSubject());
        dto.setProjectId(chat.getProjectId());
        dto.setTeamId(chat.getTeamId());
        dto.setGroupName(chat.getGroupName());
        dto.setParticipants(chat.getParticipants());
        dto.setCalendarDate(chat.getCalendarDate());
        dto.setCreatedAt(chat.getCreatedAt());
        dto.setUpdatedAt(chat.getUpdatedAt());
        return dto;
    }

    private StaffChatMessageDTO convertMessageToDTO(StaffChatMessage message) {
        StaffChatMessageDTO dto = new StaffChatMessageDTO();
        dto.setId(message.getId());
        dto.setMessageId(message.getMessageId());
        dto.setChatId(message.getChatId());
        dto.setStaffId(message.getStaffId());
        dto.setText(message.getText());
        dto.setAttachment(message.getAttachment());
        dto.setSystem(message.getSystem());
        dto.setCreatedAt(message.getCreatedAt());
        
        if (message.getStaffId() != null) {
            Optional<Staff> staffOpt = staffRepository.findById(message.getStaffId());
            staffOpt.ifPresent(s -> dto.setStaffName(s.getFirstName() + " " + s.getLastName()));
        }
        
        return dto;
    }
}