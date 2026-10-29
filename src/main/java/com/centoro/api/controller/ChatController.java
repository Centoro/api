package com.centoro.api.controller;

import com.centoro.api.dto.ChatDTO;
import com.centoro.api.dto.ChatMessageDTO;
import com.centoro.api.dto.ChatRequest;
import com.centoro.api.entities.CalendarNote;
import com.centoro.api.entities.Chat;
import com.centoro.api.entities.ChatMessage;
import com.centoro.api.entities.Client;
import com.centoro.api.entities.JobCard;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.repositories.StaffRepository;
import com.centoro.api.repositories.JobCardRepository;
import com.centoro.api.service.ChatService;
import com.centoro.api.service.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired private ChatService chatService;
    @Autowired private ClientRepository clientRepository;
    @Autowired private StaffRepository staffRepository;
    @Autowired private JobCardRepository jobCardRepository;
    @Autowired private CalendarNoteService calendarNoteService;

    @GetMapping
    public ResponseEntity<?> getAllChats() {
        List<Chat> chats = chatService.getAllChats();
        List<ChatDTO> chatDTOs = chats.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", chatDTOs));
    }

    @GetMapping("/open")
    public ResponseEntity<?> getOpenChats() {
        List<Chat> chats = chatService.getOpenChats();
        List<ChatDTO> chatDTOs = chats.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", chatDTOs));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getChatsByClient(@PathVariable String clientId) {
        List<Chat> chats = chatService.getChatsByClient(clientId);
        List<ChatDTO> chatDTOs = chats.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", chatDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable Long id) {
        Optional<Chat> chatOpt = chatService.getChatById(id);
        if (chatOpt.isEmpty()) return ResponseEntity.notFound().build();
        ChatDTO dto = convertToDTO(chatOpt.get());
        dto.setMessages(chatService.getMessages(id).stream().map(this::convertMessageToDTO).collect(Collectors.toList()));
        return ResponseEntity.ok(Map.of("success", true, "data", dto));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable Long id) {
        List<ChatMessage> messages = chatService.getMessages(id);
        List<ChatMessageDTO> messageDTOs = messages.stream().map(this::convertMessageToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", messageDTOs));
    }

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequest request) {
        if (request.getClientId() == null) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Client ID is required"));
        if (!clientRepository.existsById(request.getClientId())) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Client not found"));
        
        Chat chat = new Chat();
        chat.setClientId(request.getClientId());
        chat.setSubject(request.getSubject());
        chat.setPriority(request.getPriority() != null ? request.getPriority() : "medium");
        chat.setCalendarDate(request.getCalendarDate());
        chat.setLinkedJobId(request.getLinkedJobId());
        
        Chat saved = chatService.createChat(chat);
        if (request.getText() != null && !request.getText().isEmpty()) {
            ChatMessage message = new ChatMessage();
            message.setChatId(saved.getId());
            message.setFrom("client");
            message.setText(request.getText());
            chatService.addMessage(message);
        }
        return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(saved), "message", "Chat created successfully"));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<?> addMessage(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<Chat> chatOpt = chatService.getChatById(id);
        if (chatOpt.isEmpty()) return ResponseEntity.notFound().build();
        
        String from = (String) request.get("from");
        String text = (String) request.get("text");
        String staffId = request.get("staffId") != null ? request.get("staffId").toString() : null;
        
        if (text == null || text.isEmpty()) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Message text is required"));
        
        ChatMessage message = new ChatMessage();
        message.setChatId(id);
        message.setFrom(from != null ? from : "client");
        message.setText(text);
        message.setStaffId(staffId);
        
        ChatMessage saved = chatService.addMessage(message);
        if ("client".equals(from)) chatService.updateChatStatus(id, "open");
        
        return ResponseEntity.ok(Map.of("success", true, "data", convertMessageToDTO(saved), "message", "Message sent successfully"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateChatStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.isEmpty()) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Status is required"));
        try {
            Chat updated = chatService.updateChatStatus(id, status);
            return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(updated), "message", "Chat status updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable Long id) {
        if (!chatService.getChatById(id).isPresent()) return ResponseEntity.notFound().build();
        chatService.deleteChat(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Chat deleted successfully"));
    }

    @PostMapping("/{id}/convert-to-job")
    public ResponseEntity<?> convertChatToJob(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<Chat> chatOpt = chatService.getChatById(id);
        if (chatOpt.isEmpty()) return ResponseEntity.notFound().build();
        Chat chat = chatOpt.get();
        String title = (String) request.get("title");
        if (title == null || title.isEmpty()) title = chat.getSubject();
        String assigneeId = request.get("assigneeId") != null ? request.get("assigneeId").toString() : null;
        
        JobCard jobCard = new JobCard();
        jobCard.setJobCardId("JC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        jobCard.setTitle(title);
        jobCard.setClientId(chat.getClientId());
        jobCard.setProjectId(null);
        jobCard.setAssigneeId(assigneeId);
        jobCard.setPriority(chat.getPriority());
        jobCard.setStatus("in_progress");
        jobCard.setDueDate(new Date(System.currentTimeMillis() + 7 * 86400000));
        jobCard.setHours(1.0);
        jobCard.setService(chat.getSubject());
        jobCard.setType("Client Chat Conversion");
        jobCard.setDescription("Converted from client chat: " + chat.getSubject());
        jobCard.setLoggedBy("System");
        jobCard.setLoggedAt(new Date());
        jobCard.setSource("chat");
        
        JobCard saved = jobCardRepository.save(jobCard);
        chat.setLinkedJobId(saved.getId());
        chat.setStatus("in_progress");
        chat.setUpdatedAt(new Date());
        chatService.updateChatStatus(id, "in_progress");
        
        return ResponseEntity.ok(Map.of("success", true, "data", Map.of("chat", convertToDTO(chat), "jobCard", saved), "message", "Chat converted to job card successfully"));
    }

    @PostMapping("/{id}/link-calendar")
    public ResponseEntity<?> linkChatToCalendar(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Chat> chatOpt = chatService.getChatById(id);
        if (chatOpt.isEmpty()) return ResponseEntity.notFound().build();
        Chat chat = chatOpt.get();
        String date = request.get("date");
        if (date == null || date.isEmpty()) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Date is required (YYYY-MM-DD)"));
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid date format. Use YYYY-MM-DD"));
        
        chat.setCalendarDate(java.sql.Date.valueOf(date));
        chat.setUpdatedAt(new Date());
        chatService.updateChatStatus(id, chat.getStatus());
        
        Client client = clientRepository.findById(chat.getClientId()).orElse(null);
        CalendarNote note = new CalendarNote();
        note.setDate(date);
        note.setText("💬 Client Follow-up: " + chat.getSubject() + (client != null ? " (" + client.getName() + ")" : ""));
        note.setImportance(chat.getPriority());
        note.setCreatedBy("System");
        calendarNoteService.createNote(note);
        
        return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(chat), "message", "Chat linked to calendar successfully"));
    }

    @GetMapping("/client/{clientId}/open")
    public ResponseEntity<?> getOpenChatsByClient(@PathVariable String clientId) {
        List<Chat> chats = chatService.getChatsByClient(clientId).stream().filter(c -> !"resolved".equals(c.getStatus())).collect(Collectors.toList());
        List<ChatDTO> chatDTOs = chats.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", chatDTOs));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getChatStats() {
        long open = chatService.getOpenChats().size();
        long inProgress = chatService.getAllChats().stream().filter(c -> "in_progress".equals(c.getStatus())).count();
        long resolved = chatService.getAllChats().stream().filter(c -> "resolved".equals(c.getStatus())).count();
        long linkedJobs = chatService.getAllChats().stream().filter(c -> c.getLinkedJobId() != null).count();
        Map<String, Object> stats = Map.of("open", open, "inProgress", inProgress, "resolved", resolved, "linkedJobs", linkedJobs);
        return ResponseEntity.ok(Map.of("success", true, "data", stats));
    }

    private ChatDTO convertToDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setId(chat.getId());
        dto.setChatId(chat.getChatId());
        dto.setClientId(chat.getClientId());
        clientRepository.findById(chat.getClientId()).ifPresent(c -> dto.setClientName(c.getName()));
        dto.setSubject(chat.getSubject());
        dto.setStatus(chat.getStatus());
        dto.setPriority(chat.getPriority());
        dto.setCalendarDate(chat.getCalendarDate());
        dto.setLinkedJobId(chat.getLinkedJobId());
        dto.setCreatedAt(chat.getCreatedAt());
        dto.setUpdatedAt(chat.getUpdatedAt());
        return dto;
    }

    private ChatMessageDTO convertMessageToDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setMessageId(message.getMessageId());
        dto.setChatId(message.getChatId());
        dto.setFrom(message.getFrom());
        dto.setStaffId(message.getStaffId());
        dto.setText(message.getText());
        dto.setAttachment(message.getAttachment());
        dto.setCreatedAt(message.getCreatedAt());
        if (message.getStaffId() != null) {
            staffRepository.findById(message.getStaffId()).ifPresent(s -> dto.setStaffName(s.getFirstName() + " " + s.getLastName()));
        }
        return dto;
    }
}
