package com.centoro.api.service;

import com.centoro.api.entities.Client;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.ProjectRepository;
import com.centoro.api.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;  // ✅ THIS IS THE MISSING IMPORT

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private IdGenerator idGenerator;

    // ============ CREATE ============
    
    @Transactional
    public Client createClient(Client client) {
        client.setClientId(idGenerator.generateClientId());
        
        if (client.getCode() == null || client.getCode().isEmpty()) {
            client.setCode(generateClientCode(client));
        }
        
        client.setCreatedAt(new Date());
        client.setUpdatedAt(new Date());
        if (client.getStatus() == null) client.setStatus(Client.ClientStatus.active);
        if (client.getTotalSpent() == null) client.setTotalSpent(0.0);
        if (client.getProjectsCount() == null) client.setProjectsCount(0);
        return clientRepository.save(client);
    }

    // ============ READ (GET ALL) ============
    
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    public List<Client> getAllClientsWithDeleted() {
        return clientRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Client> searchClients(String query) {
        return clientRepository.findByNameContainingIgnoreCase(query);
    }
    
    @Transactional(readOnly = true)
    public List<Client> getClientsByStatus(Client.ClientStatus status) {
        return clientRepository.findByStatus(status);
    }

    // ============ READ (GET BY ID/FIELD) ============
    
    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Client> getClientByCode(String code) {
        return clientRepository.findByCode(code);
    }
    
    @Transactional(readOnly = true)
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // ============ UPDATE ============
    
    @Transactional
    public Client updateClient(Long id, Client client) {
        Client existing = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        
        existing.setName(client.getName());
        existing.setFirstName(client.getFirstName());
        existing.setMiddleName(client.getMiddleName());
        existing.setSurname(client.getSurname());
        existing.setInitials(client.getInitials());
        existing.setIdNumber(client.getIdNumber());
        existing.setDob(client.getDob());
        existing.setEmail(client.getEmail());
        existing.setPhone(client.getPhone());
        existing.setAddress(client.getAddress());
        existing.setWebsite(client.getWebsite());
        existing.setCompanyName(client.getCompanyName());
        existing.setCompanyCK(client.getCompanyCK());
        existing.setStatus(client.getStatus());
        existing.setUpdatedAt(new Date());
        return clientRepository.save(existing);
    }
    
    @Transactional
    public Client updateClientStats(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        
        Double totalSpent = invoiceRepository.findByClientId(id).stream()
            .mapToDouble(i -> i.getTotal() != null ? i.getTotal() : 0.0)
            .sum();
        
        Long projectCount = projectRepository.findActiveByClientId(id).stream().count();
        
        client.setTotalSpent(totalSpent);
        client.setProjectsCount(projectCount.intValue());
        client.setUpdatedAt(new Date());
        
        return clientRepository.save(client);
    }

    // ============ DELETE ============
    
    @Transactional
    public void softDeleteClient(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        client.setDeletedAt(new Date());
        clientRepository.save(client);
    }
    
    @Transactional
    public void restoreClient(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        client.setDeletedAt(null);
        clientRepository.save(client);
    }
    
    @Transactional
    public void hardDeleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // ============ STATISTICS ============
    
    @Transactional(readOnly = true)
    public Map<String, Object> getClientStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalClients", clientRepository.count());
        stats.put("activeClients", clientRepository.findByStatus(Client.ClientStatus.active).size());
        stats.put("inactiveClients", clientRepository.findByStatus(Client.ClientStatus.inactive).size());
        
        Double totalBilled = clientRepository.findAll().stream()
            .mapToDouble(c -> c.getTotalSpent() != null ? c.getTotalSpent() : 0.0)
            .sum();
        stats.put("totalBilled", totalBilled);
        
        return stats;
    }

    // ============ CLIENT SERVICES ============
    
    @Transactional(readOnly = true)
    public List<String> getClientServices(Long clientId) {
        Set<String> services = new HashSet<>();
        
        invoiceRepository.findByClientId(clientId).forEach(invoice -> {
            if (invoice.getService() != null && !invoice.getService().isEmpty()) {
                services.add(invoice.getService());
            }
        });
        
        projectRepository.findActiveByClientId(clientId).forEach(project -> {
            if (project.getService() != null && !project.getService().isEmpty()) {
                services.add(project.getService());
            }
        });
        
        return new ArrayList<>(services);
    }

    // ============ HELPER METHODS ============
    
    private String generateClientCode(Client client) {
        String base = clientCodeBaseFromClient(client);
        return makeUniqueClientCode(base);
    }
    
    private String clientCodeBaseFromClient(Client client) {
        String firstName = client.getFirstName() != null ? client.getFirstName() : "";
        String middleName = client.getMiddleName() != null ? client.getMiddleName() : "";
        String surname = client.getSurname() != null ? client.getSurname() : "";
        String companyName = client.getCompanyName() != null ? client.getCompanyName() : "";
        String name = client.getName() != null ? client.getName() : "";
        
        String personName = Stream.of(firstName, middleName, surname)
            .filter(s -> s != null && !s.isEmpty())
            .collect(Collectors.joining(" "));
        
        String source = personName.isEmpty() ? companyName : personName;
        if (source.isEmpty()) source = name;
        if (source.isEmpty()) return "CL";
        
        String[] parts = source.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        }
        
        return Arrays.stream(parts)
            .filter(p -> !p.isEmpty())
            .map(p -> p.substring(0, 1).toUpperCase())
            .limit(3)
            .collect(Collectors.joining());
    }
    
    private String makeUniqueClientCode(String base) {
        String cleanBase = base.toUpperCase();
        int maxSeq = 0;
        
        for (Client c : clientRepository.findAll()) {
            String code = c.getCode() != null ? c.getCode().toUpperCase() : "";
            if (code.equals(cleanBase)) {
                maxSeq = Math.max(maxSeq, 1);
            } else if (code.startsWith(cleanBase)) {
                String tail = code.substring(cleanBase.length());
                try {
                    int num = Integer.parseInt(tail);
                    maxSeq = Math.max(maxSeq, num);
                } catch (NumberFormatException ignored) {}
            }
        }
        
        return cleanBase + String.format("%02d", maxSeq + 1);
    }
}