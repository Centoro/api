package com.centoro.api.service;

import com.centoro.api.dto.SendEmailRequest;
import com.centoro.api.entities.Client;
import com.centoro.api.entities.EmailAccount;
import com.centoro.api.entities.EmailCampaign;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.repositories.EmailAccountRepository;
import com.centoro.api.repositories.EmailCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private EmailAccountRepository emailAccountRepository;

    @Autowired
    private EmailCampaignRepository emailCampaignRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<EmailAccount> getAllAccounts() { return emailAccountRepository.findAll(); }
    public Optional<EmailAccount> getAccountById(Long id) { return emailAccountRepository.findById(id); }

    public EmailAccount createAccount(EmailAccount account) {
        account.setAccountId("EAC-" + UUID.randomUUID().toString().substring(0, 6));
        account.setCreatedAt(new Date());
        if (account.getStatus() == null) account.setStatus("connected");
        return emailAccountRepository.save(account);
    }

    public EmailAccount updateAccount(Long id, EmailAccount account) {
        EmailAccount existing = emailAccountRepository.findById(id).orElseThrow();
        existing.setLabel(account.getLabel());
        existing.setEmail(account.getEmail());
        existing.setStatus(account.getStatus());
        existing.setUpdatedAt(new Date());
        return emailAccountRepository.save(existing);
    }

    public void deleteAccount(Long id) { emailAccountRepository.deleteById(id); }

    public List<EmailCampaign> getAllCampaigns() { return emailCampaignRepository.findAll(); }
    public Optional<EmailCampaign> getCampaignById(Long id) { return emailCampaignRepository.findById(id); }
    public void deleteCampaign(Long id) { emailCampaignRepository.deleteById(id); }

    public EmailCampaign sendEmail(SendEmailRequest request) {
        // FIX: request.getClientId() is now String
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));
        EmailAccount account = emailAccountRepository.findById(request.getAccountId()).orElseThrow(() -> new RuntimeException("Email account not found"));
        
        EmailCampaign campaign = new EmailCampaign();
        campaign.setCampaignId("EML-" + UUID.randomUUID().toString().substring(0, 6));
        campaign.setSubject(request.getSubject());
        campaign.setBody(request.getBody());
        campaign.setRecipient(client.getEmail());
        campaign.setRecipientsCount(1);
        campaign.setProvider(account.getLabel());
        campaign.setStatus("sent");
        campaign.setSentAt(new Date());
        campaign.setCreatedAt(new Date());
        
        // Actual sending logic goes here
        
        return emailCampaignRepository.save(campaign);
    }

    // FIX: Changed from Long to String
    public List<String> getClientServices(String clientId) {
        return List.of("General Service", "Consultation"); // Placeholder logic
    }
}
