package com.centoro.api.controller;

import com.centoro.api.entities.*;
import com.centoro.api.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/phone")
@CrossOrigin(origins = "*")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    // ============ PHONE LINES ============
    
    @GetMapping("/lines")
    public ResponseEntity<?> getAllPhoneLines() {
        List<PhoneLine> lines = phoneService.getAllPhoneLines();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", lines
        ));
    }

    @GetMapping("/lines/active")
    public ResponseEntity<?> getActivePhoneLines() {
        List<PhoneLine> lines = phoneService.getActivePhoneLines();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", lines
        ));
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<?> getPhoneLine(@PathVariable Long id) {
        Optional<PhoneLine> line = phoneService.getPhoneLineById(id);
        if (line.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", line.get()
        ));
    }

    @PostMapping("/lines")
    public ResponseEntity<?> createPhoneLine(@RequestBody PhoneLine line) {
        PhoneLine saved = phoneService.createPhoneLine(line);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Phone line created successfully"
        ));
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<?> deletePhoneLine(@PathVariable Long id) {
        if (!phoneService.getPhoneLineById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        phoneService.deletePhoneLine(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Phone line deleted successfully"
        ));
    }

    // ============ SMS PROVIDERS ============
    
    @GetMapping("/providers")
    public ResponseEntity<?> getAllSmsProviders() {
        List<SmsProvider> providers = phoneService.getAllSmsProviders();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", providers.stream().map(this::toSmsProviderResponse).toList()
        ));
    }

    @GetMapping("/providers/active")
    public ResponseEntity<?> getActiveSmsProviders() {
        List<SmsProvider> providers = phoneService.getActiveSmsProviders();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", providers.stream().map(this::toSmsProviderResponse).toList()
        ));
    }

    @GetMapping("/providers/{id}")
    public ResponseEntity<?> getSmsProvider(@PathVariable Long id) {
        Optional<SmsProvider> provider = phoneService.getSmsProviderById(id);
        if (provider.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", toSmsProviderResponse(provider.get())
        ));
    }

    @PostMapping("/providers")
    public ResponseEntity<?> createSmsProvider(@RequestBody SmsProvider provider) {
        SmsProvider saved = phoneService.createSmsProvider(provider);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", toSmsProviderResponse(saved),
            "message", "SMS provider connected successfully"
        ));
    }

    @DeleteMapping("/providers/{id}")
    public ResponseEntity<?> deleteSmsProvider(@PathVariable Long id) {
        if (!phoneService.getSmsProviderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        phoneService.deleteSmsProvider(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "SMS provider disconnected successfully"
        ));
    }

    // ============ PHONE BOOK ============
    
    @GetMapping("/contacts")
    public ResponseEntity<?> getAllContacts() {
        List<PhoneBookContact> contacts = phoneService.getAllContacts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contacts
        ));
    }

    @GetMapping("/contacts/category/{category}")
    public ResponseEntity<?> getContactsByCategory(@PathVariable String category) {
        List<PhoneBookContact> contacts = phoneService.getContactsByCategory(category);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contacts
        ));
    }

    @GetMapping("/contacts/client/{clientId}")
    public ResponseEntity<?> getContactsByClient(@PathVariable Long clientId) {
        List<PhoneBookContact> contacts = phoneService.getContactsByClient(clientId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contacts
        ));
    }

    @GetMapping("/contacts/speed-dial")
    public ResponseEntity<?> getSpeedDialContacts() {
        List<PhoneBookContact> contacts = phoneService.getSpeedDialContacts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contacts
        ));
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<?> getContact(@PathVariable Long id) {
        Optional<PhoneBookContact> contact = phoneService.getContactById(id);
        if (contact.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contact.get()
        ));
    }

    @PostMapping("/contacts")
    public ResponseEntity<?> createContact(@RequestBody PhoneBookContact contact) {
        try {
            PhoneBookContact saved = phoneService.createContact(contact);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", saved,
                "message", "Contact added to phone book"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody PhoneBookContact contact) {
        try {
            PhoneBookContact updated = phoneService.updateContact(id, contact);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", updated,
                "message", "Contact updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        if (!phoneService.getContactById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        phoneService.deleteContact(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Contact deleted successfully"
        ));
    }

    // ============ SMS CAMPAIGNS ============
    
    @GetMapping("/campaigns")
    public ResponseEntity<?> getAllSmsCampaigns() {
        List<SmsCampaign> campaigns = phoneService.getAllSmsCampaigns();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", campaigns
        ));
    }

    @GetMapping("/campaigns/{id}")
    public ResponseEntity<?> getSmsCampaign(@PathVariable Long id) {
        Optional<SmsCampaign> campaign = phoneService.getSmsCampaignById(id);
        if (campaign.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", campaign.get()
        ));
    }

    @PostMapping("/campaigns")
    public ResponseEntity<?> createSmsCampaign(@RequestBody com.centoro.api.dto.SendSmsCampaignRequest request) {
        try {
            SmsCampaign saved = phoneService.sendSmsCampaign(request);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", saved,
                "message", "SMS sent successfully"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    private Map<String, Object> toSmsProviderResponse(SmsProvider provider) {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("id", provider.getId());
        response.put("providerId", provider.getProviderId());
        response.put("label", provider.getLabel());
        response.put("provider", provider.getProvider());
        response.put("senderId", provider.getSenderId());
        response.put("status", provider.getStatus());
        response.put("createdAt", provider.getCreatedAt());
        response.put("updatedAt", provider.getUpdatedAt());
        return response;
    }

    // ============ ISP PORTS ============
    
    @GetMapping("/ports")
    public ResponseEntity<?> getAllIspPorts() {
        List<IspPort> ports = phoneService.getAllIspPorts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", ports
        ));
    }

    @GetMapping("/ports/direction/{direction}")
    public ResponseEntity<?> getIspPortsByDirection(@PathVariable String direction) {
        List<IspPort> ports = phoneService.getIspPortsByDirection(direction);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", ports
        ));
    }

    @GetMapping("/ports/{id}")
    public ResponseEntity<?> getIspPort(@PathVariable Long id) {
        Optional<IspPort> port = phoneService.getIspPortById(id);
        if (port.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", port.get()
        ));
    }

    @PostMapping("/ports")
    public ResponseEntity<?> createIspPort(@RequestBody IspPort port) {
        IspPort saved = phoneService.createIspPort(port);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "ISP port configured successfully"
        ));
    }

    @DeleteMapping("/ports/{id}")
    public ResponseEntity<?> deleteIspPort(@PathVariable Long id) {
        if (!phoneService.getIspPortById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        phoneService.deleteIspPort(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "ISP port removed successfully"
        ));
    }
}