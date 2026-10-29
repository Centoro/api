package com.centoro.api.controller;

import com.centoro.api.dto.EmailAccountDTO;
import com.centoro.api.dto.EmailCampaignDTO;
import com.centoro.api.dto.SendEmailRequest;
import com.centoro.api.entities.EmailAccount;
import com.centoro.api.entities.EmailCampaign;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        List<EmailAccount> accounts = emailService.getAllAccounts();
        List<EmailAccountDTO> accountDTOs = accounts.stream().map(this::convertAccountToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", accountDTOs));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        Optional<EmailAccount> accountOpt = emailService.getAccountById(id);
        if (accountOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", convertAccountToDTO(accountOpt.get())));
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody EmailAccount account) {
        EmailAccount saved = emailService.createAccount(account);
        return ResponseEntity.ok(Map.of("success", true, "data", convertAccountToDTO(saved), "message", "Email account connected successfully"));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody EmailAccount account) {
        try {
            EmailAccount updated = emailService.updateAccount(id, account);
            return ResponseEntity.ok(Map.of("success", true, "data", convertAccountToDTO(updated), "message", "Email account updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        if (!emailService.getAccountById(id).isPresent()) return ResponseEntity.notFound().build();
        emailService.deleteAccount(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Email account disconnected successfully"));
    }

    @GetMapping("/campaigns")
    public ResponseEntity<?> getAllCampaigns() {
        List<EmailCampaign> campaigns = emailService.getAllCampaigns();
        List<EmailCampaignDTO> campaignDTOs = campaigns.stream().map(this::convertCampaignToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", campaignDTOs));
    }

    @GetMapping("/campaigns/{id}")
    public ResponseEntity<?> getCampaign(@PathVariable Long id) {
        Optional<EmailCampaign> campaignOpt = emailService.getCampaignById(id);
        if (campaignOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", convertCampaignToDTO(campaignOpt.get())));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailRequest request) {
        if (request.getAccountId() == null || request.getClientId() == null || request.getSubject() == null || request.getBody() == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Missing required fields"));
        }
        try {
            EmailCampaign saved = emailService.sendEmail(request);
            return ResponseEntity.ok(Map.of("success", true, "data", convertCampaignToDTO(saved), "message", "Email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Failed to send email: " + e.getMessage()));
        }
    }

    @DeleteMapping("/campaigns/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) {
        if (!emailService.getCampaignById(id).isPresent()) return ResponseEntity.notFound().build();
        emailService.deleteCampaign(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Email campaign deleted successfully"));
    }

    @GetMapping("/clients/{clientId}/services")
    public ResponseEntity<?> getClientServices(@PathVariable String clientId) {
        // FIX: clientId is String now
        if (!clientRepository.existsById(clientId)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Client not found"));
        }
        List<String> services = emailService.getClientServices(clientId);
        return ResponseEntity.ok(Map.of("success", true, "data", services));
    }

    private EmailAccountDTO convertAccountToDTO(EmailAccount account) {
        EmailAccountDTO dto = new EmailAccountDTO();
        dto.setId(account.getId());
        dto.setAccountId(account.getAccountId());
        dto.setType(account.getType());
        dto.setLabel(account.getLabel());
        dto.setEmail(account.getEmail());
        dto.setStatus(account.getStatus());
        dto.setConnectedAt(account.getConnectedAt());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }

    private EmailCampaignDTO convertCampaignToDTO(EmailCampaign campaign) {
        EmailCampaignDTO dto = new EmailCampaignDTO();
        dto.setId(campaign.getId());
        dto.setCampaignId(campaign.getCampaignId());
        dto.setSubject(campaign.getSubject());
        dto.setBody(campaign.getBody());
        dto.setRecipient(campaign.getRecipient());
        dto.setRecipientsCount(campaign.getRecipientsCount());
        dto.setProvider(campaign.getProvider());
        dto.setAttachments(campaign.getAttachments());
        dto.setStatus(campaign.getStatus());
        dto.setSentAt(campaign.getSentAt());
        dto.setCreatedAt(campaign.getCreatedAt());
        return dto;
    }
}
