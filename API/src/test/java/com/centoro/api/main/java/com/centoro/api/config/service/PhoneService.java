package com.centoro.api.service;

import com.centoro.api.dto.SendSmsCampaignRequest;
import com.centoro.api.entities.*;
import com.centoro.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    
    @Autowired
    private PhoneLineRepository phoneLineRepository;
    
    @Autowired
    private SmsProviderRepository smsProviderRepository;
    
    @Autowired
    private PhoneBookContactRepository phoneBookContactRepository;
    
    @Autowired
    private SmsCampaignRepository smsCampaignRepository;
    
    @Autowired
    private IspPortRepository ispPortRepository;
    
    // ============ PHONE LINES ============
    
    @Transactional
    public PhoneLine createPhoneLine(PhoneLine line) {
        line.setLineId("PLN-" + String.format("%04d", System.currentTimeMillis() % 10000));
        line.setCreatedAt(new Date());
        line.setUpdatedAt(new Date());
        if (line.getStatus() == null) line.setStatus("active");
        return phoneLineRepository.save(line);
    }
    
    public List<PhoneLine> getAllPhoneLines() {
        return phoneLineRepository.findAll();
    }
    
    public List<PhoneLine> getActivePhoneLines() {
        return phoneLineRepository.findByStatus("active");
    }
    
    public Optional<PhoneLine> getPhoneLineById(Long id) {
        return phoneLineRepository.findById(id);
    }
    
    @Transactional
    public void deletePhoneLine(Long id) {
        phoneLineRepository.deleteById(id);
    }
    
    // ============ SMS PROVIDERS ============
    
    @Transactional
    public SmsProvider createSmsProvider(SmsProvider provider) {
        provider.setProviderId("SPR-" + String.format("%04d", System.currentTimeMillis() % 10000));
        provider.setCreatedAt(new Date());
        provider.setUpdatedAt(new Date());
        if (provider.getStatus() == null) provider.setStatus("connected");
        return smsProviderRepository.save(provider);
    }
    
    public List<SmsProvider> getAllSmsProviders() {
        return smsProviderRepository.findAll();
    }
    
    public List<SmsProvider> getActiveSmsProviders() {
        return smsProviderRepository.findByStatus("connected");
    }
    
    public Optional<SmsProvider> getSmsProviderById(Long id) {
        return smsProviderRepository.findById(id);
    }
    
    @Transactional
    public void deleteSmsProvider(Long id) {
        smsProviderRepository.deleteById(id);
    }
    
    // ============ PHONE BOOK ============
    
    @Transactional
    public PhoneBookContact createContact(PhoneBookContact contact) {
        contact.setContactId("PBK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        contact.setCreatedAt(new Date());
        contact.setUpdatedAt(new Date());
        if (contact.getSpeedDial() != null) {
            // Check if speed dial already taken
            Optional<PhoneBookContact> existing = phoneBookContactRepository.findBySpeedDial(contact.getSpeedDial());
            if (existing.isPresent()) {
                throw new RuntimeException("Speed dial number " + contact.getSpeedDial() + " is already taken");
            }
        }
        return phoneBookContactRepository.save(contact);
    }
    
    public List<PhoneBookContact> getAllContacts() {
        return phoneBookContactRepository.findAll();
    }
    
    public List<PhoneBookContact> getContactsByCategory(String category) {
        return phoneBookContactRepository.findByCategory(category);
    }
    
    public List<PhoneBookContact> getContactsByClient(Long clientId) {
        return phoneBookContactRepository.findByClientId(clientId);
    }
    
    public List<PhoneBookContact> getSpeedDialContacts() {
        return phoneBookContactRepository.findBySpeedDialNotNullOrderBySpeedDialAsc();
    }
    
    public Optional<PhoneBookContact> getContactById(Long id) {
        return phoneBookContactRepository.findById(id);
    }
    
    @Transactional
    public PhoneBookContact updateContact(Long id, PhoneBookContact contact) {
        PhoneBookContact existing = phoneBookContactRepository.findById(id).orElseThrow();
        existing.setName(contact.getName());
        existing.setNumber(contact.getNumber());
        existing.setCategory(contact.getCategory());
        existing.setSpeedDial(contact.getSpeedDial());
        existing.setClientId(contact.getClientId());
        existing.setNotes(contact.getNotes());
        existing.setUpdatedAt(new Date());
        return phoneBookContactRepository.save(existing);
    }
    
    @Transactional
    public void deleteContact(Long id) {
        phoneBookContactRepository.deleteById(id);
    }
    
    // ============ SMS CAMPAIGNS ============
    
    @Transactional
    public SmsCampaign createSmsCampaign(SmsCampaign campaign) {
        campaign.setCampaignId("SMS-" + String.format("%04d", System.currentTimeMillis() % 10000));
        campaign.setCreatedAt(new Date());
        campaign.setSentAt(new Date());
        if (campaign.getStatus() == null) campaign.setStatus("sent");
        return smsCampaignRepository.save(campaign);
    }

    @Transactional
    public SmsCampaign sendSmsCampaign(SendSmsCampaignRequest request) {
        if (request.getProviderId() == null) throw new IllegalArgumentException("SMS provider is required");
        if (request.getMessage() == null || request.getMessage().isBlank()) throw new IllegalArgumentException("Message is required");
        if (request.getRecipients() == null || request.getRecipients().isEmpty()) throw new IllegalArgumentException("At least one recipient is required");

        SmsProvider provider = smsProviderRepository.findById(request.getProviderId())
            .orElseThrow(() -> new IllegalArgumentException("SMS provider was not found"));
        if (!"Twilio".equalsIgnoreCase(provider.getProvider())) {
            throw new IllegalArgumentException("SMS delivery is not implemented for " + provider.getProvider() + ". Configure a Twilio provider to send campaigns.");
        }
        if (provider.getApiKey() == null || provider.getApiKey().isBlank()
                || provider.getApiSecret() == null || provider.getApiSecret().isBlank()
                || provider.getSenderId() == null || provider.getSenderId().isBlank()) {
            throw new IllegalArgumentException("Twilio Account SID, Auth Token, and sender number are required");
        }

        String endpoint = "https://api.twilio.com/2010-04-01/Accounts/" + provider.getApiKey() + "/Messages.json";
        if (provider.getApiEndpoint() != null && !provider.getApiEndpoint().isBlank()
                && !endpoint.equals(provider.getApiEndpoint().trim())) {
            throw new IllegalArgumentException("Twilio API endpoint must match the configured account");
        }
        String authorization = Base64.getEncoder().encodeToString(
            (provider.getApiKey() + ":" + provider.getApiSecret()).getBytes(StandardCharsets.UTF_8));
        HttpClient client = HttpClient.newHttpClient();
        for (String recipient : request.getRecipients()) {
            if (recipient == null || recipient.isBlank()) continue;
            String body = "To=" + encode(recipient) + "&From=" + encode(provider.getSenderId())
                + "&Body=" + encode(request.getMessage());
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(endpoint))
                .header("Authorization", "Basic " + authorization)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(java.time.Duration.ofSeconds(20))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            try {
                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() < 200 || response.statusCode() >= 300) {
                    throw new IllegalArgumentException("Twilio rejected SMS delivery (HTTP " + response.statusCode() + ")");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("SMS delivery was interrupted", e);
            } catch (java.io.IOException e) {
                throw new IllegalStateException("Unable to reach the Twilio SMS API", e);
            }
        }

        SmsCampaign campaign = new SmsCampaign();
        campaign.setMessage(request.getMessage());
        campaign.setRecipient(request.getRecipient());
        campaign.setRecipientsCount(request.getRecipients().size());
        campaign.setProvider(provider.getLabel() == null ? provider.getProvider() : provider.getLabel());
        campaign.setFromLine(request.getFromLine());
        campaign.setStatus("sent");
        return createSmsCampaign(campaign);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    
    public List<SmsCampaign> getAllSmsCampaigns() {
        return smsCampaignRepository.findByOrderBySentAtDesc();
    }
    
    public Optional<SmsCampaign> getSmsCampaignById(Long id) {
        return smsCampaignRepository.findById(id);
    }
    
    // ============ ISP PORTS ============
    
    @Transactional
    public IspPort createIspPort(IspPort port) {
        port.setPortId("ISP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        port.setCreatedAt(new Date());
        port.setUpdatedAt(new Date());
        if (port.getStatus() == null) port.setStatus("open");
        return ispPortRepository.save(port);
    }
    
    public List<IspPort> getAllIspPorts() {
        return ispPortRepository.findAll();
    }
    
    public List<IspPort> getIspPortsByDirection(String direction) {
        return ispPortRepository.findByDirection(direction);
    }
    
    public Optional<IspPort> getIspPortById(Long id) {
        return ispPortRepository.findById(id);
    }
    
    @Transactional
    public void deleteIspPort(Long id) {
        ispPortRepository.deleteById(id);
    }
}