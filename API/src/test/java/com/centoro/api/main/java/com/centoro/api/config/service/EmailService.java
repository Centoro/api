package com.centoro.api.service;

import com.centoro.api.dto.SendEmailRequest;
import com.centoro.api.entities.EmailAccount;
import com.centoro.api.entities.EmailCampaign;
import com.centoro.api.repositories.EmailAccountRepository;
import com.centoro.api.repositories.EmailCampaignRepository;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

@Service
public class EmailService {
    
    @Autowired
    private EmailAccountRepository emailAccountRepository;
    
    @Autowired
    private EmailCampaignRepository emailCampaignRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // ============ ACCOUNTS ============
    
    @Transactional
    public EmailAccount createAccount(EmailAccount account) {
        account.setAccountId("EAC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        if (account.getStatus() == null) account.setStatus("connected");
        if (account.getConnectedAt() == null) account.setConnectedAt(new Date());
        return emailAccountRepository.save(account);
    }
    
    public List<EmailAccount> getAllAccounts() {
        return emailAccountRepository.findAll();
    }
    
    public Optional<EmailAccount> getAccountById(Long id) {
        return emailAccountRepository.findById(id);
    }
    
    public Optional<EmailAccount> getAccountByEmail(String email) {
        return emailAccountRepository.findByEmail(email);
    }
    
    @Transactional
    public EmailAccount updateAccount(Long id, EmailAccount account) {
        EmailAccount existing = emailAccountRepository.findById(id).orElseThrow();
        existing.setLabel(account.getLabel());
        existing.setEmail(account.getEmail());
        existing.setAppPassword(account.getAppPassword());
        existing.setImapHost(account.getImapHost());
        existing.setImapPort(account.getImapPort());
        existing.setUsername(account.getUsername());
        existing.setPasswordHash(account.getPasswordHash());
        existing.setSmtpHost(account.getSmtpHost());
        existing.setSmtpPort(account.getSmtpPort());
        existing.setApiKey(account.getApiKey());
        existing.setFromEmail(account.getFromEmail());
        existing.setAiProviderId(account.getAiProviderId());
        existing.setStatus(account.getStatus());
        existing.setUpdatedAt(new Date());
        return emailAccountRepository.save(existing);
    }
    
    @Transactional
    public void deleteAccount(Long id) {
        emailAccountRepository.deleteById(id);
    }
    
    // ============ CAMPAIGNS ============
    
    @Transactional
    public EmailCampaign sendEmail(SendEmailRequest request) {
        Optional<EmailAccount> accountOpt = emailAccountRepository.findById(request.getAccountId());
        if (accountOpt.isEmpty()) {
            throw new RuntimeException("Email account not found");
        }
        
        EmailAccount account = accountOpt.get();
        
        String smtpHost = account.getSmtpHost();
        String accountType = account.getType() == null ? "" : account.getType().toLowerCase(Locale.ROOT);
        if ((smtpHost == null || smtpHost.isBlank()) && accountType.equals("google")) smtpHost = "smtp.gmail.com";
        if ((smtpHost == null || smtpHost.isBlank()) && accountType.equals("microsoft")) smtpHost = "smtp.office365.com";
        String username = account.getUsername() == null || account.getUsername().isBlank()
            ? account.getEmail() : account.getUsername();
        String password = account.getAppPassword() == null || account.getAppPassword().isBlank()
            ? account.getPasswordHash() : account.getAppPassword();
        if (smtpHost == null || smtpHost.isBlank() || username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new RuntimeException("This account needs a valid SMTP host, username, and app password before it can send email");
        }
        if (request.getRecipientEmail() == null || request.getRecipientEmail().isBlank()) {
            throw new RuntimeException("A recipient email address is required");
        }

        int smtpPort;
        try {
            smtpPort = account.getSmtpPort() == null || account.getSmtpPort().isBlank()
                ? (accountType.equals("google") || accountType.equals("microsoft") ? 587 : 465)
                : Integer.parseInt(account.getSmtpPort());
        } catch (NumberFormatException e) {
            throw new RuntimeException("SMTP port must be a valid number", e);
        }
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());
        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", String.valueOf(smtpPort != 465));
        mailProperties.put("mail.smtp.ssl.enable", String.valueOf(smtpPort == 465));
        mailProperties.put("mail.smtp.connectiontimeout", "10000");
        mailProperties.put("mail.smtp.timeout", "10000");
        mailProperties.put("mail.smtp.writetimeout", "10000");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(account.getFromEmail() == null || account.getFromEmail().isBlank()
                ? account.getEmail() : account.getFromEmail());
            helper.setTo(request.getRecipientEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getBody(), true);
            if (request.getAttachments() != null) {
                for (Map<String, String> attachment : request.getAttachments()) {
                    String dataUrl = attachment.get("dataUrl");
                    if (dataUrl == null || !dataUrl.startsWith("data:")) {
                        throw new RuntimeException("Attachment " + attachment.getOrDefault("name", "file") + " is not ready to send; reselect it and try again");
                    }
                    int comma = dataUrl.indexOf(',');
                    if (comma < 0) throw new RuntimeException("Attachment data is invalid");
                    String metadata = dataUrl.substring(0, comma);
                    String contentType = metadata.substring(5).split(";", 2)[0];
                    byte[] bytes = Base64.getDecoder().decode(dataUrl.substring(comma + 1));
                    helper.addAttachment(attachment.getOrDefault("name", "attachment"),
                        new ByteArrayResource(bytes), contentType.isBlank() ? "application/octet-stream" : contentType);
                }
            }
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Email delivery failed: " + e.getMessage(), e);
        }
        
        EmailCampaign campaign = new EmailCampaign();
        campaign.setCampaignId("EML-" + String.format("%04d", System.currentTimeMillis() % 10000));
        campaign.setSubject(request.getSubject());
        campaign.setBody(request.getBody());
        campaign.setRecipient(request.getRecipientName() != null ? request.getRecipientName() : request.getRecipientEmail());
        campaign.setRecipientsCount(1);
        campaign.setProvider(account.getLabel() != null ? 
            account.getLabel() : account.getEmail());
        campaign.setStatus("sent");
        campaign.setSentAt(new Date());
        campaign.setCreatedAt(new Date());
        
        // Save attachments as JSON if present
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            try {
                List<Map<String, String>> attachmentMetadata = request.getAttachments().stream().map(attachment -> {
                    Map<String, String> metadata = new HashMap<>(attachment);
                    metadata.remove("dataUrl");
                    return metadata;
                }).toList();
                campaign.setAttachments(objectMapper.writeValueAsString(attachmentMetadata));
            } catch (Exception e) {
                campaign.setAttachments("[]");
            }
        }
        
        return emailCampaignRepository.save(campaign);
    }
    
    public List<EmailCampaign> getAllCampaigns() {
        return emailCampaignRepository.findByOrderBySentAtDesc();
    }
    
    public Optional<EmailCampaign> getCampaignById(Long id) {
        return emailCampaignRepository.findById(id);
    }
    
    public List<EmailCampaign> getCampaignsByRecipient(String recipient) {
        return emailCampaignRepository.findByRecipient(recipient);
    }
    
    @Transactional
    public void deleteCampaign(Long id) {
        emailCampaignRepository.deleteById(id);
    }
    
    // ============ CLIENT SERVICES ============
    
    public List<String> getClientServices(Long clientId) {
        Set<String> services = new HashSet<>();
        
        // Get services from invoices
        invoiceRepository.findByClientId(clientId).forEach(invoice -> {
            if (invoice.getService() != null && !invoice.getService().isEmpty()) {
                services.add(invoice.getService());
            }
            // Parse items from JSON if present
            if (invoice.getItems() != null) {
                try {
                    List<Map<String, Object>> items = objectMapper.readValue(
                        invoice.getItems(), 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
                    );
                    for (Map<String, Object> item : items) {
                        Object desc = item.get("description");
                        if (desc != null) {
                            services.add(desc.toString());
                        }
                    }
                } catch (Exception e) {
                    // Ignore parsing errors
                }
            }
        });
        
        // Get services from projects
        projectRepository.findActiveByClientId(clientId).forEach(project -> {
            if (project.getService() != null && !project.getService().isEmpty()) {
                services.add(project.getService());
            }
        });
        
        return new ArrayList<>(services);
    }
}