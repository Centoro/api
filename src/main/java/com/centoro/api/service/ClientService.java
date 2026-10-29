package com.centoro.api.service;

import com.centoro.api.entities.Client;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired private ClientRepository clientRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private InvoiceRepository invoiceRepository;

    public List<Client> getAllClients() { return clientRepository.findAllActive(); }
    public List<Client> getAllClientsWithDeleted() { return clientRepository.findAll(); }
    public List<Client> searchClients(String query) { return clientRepository.searchClients(query); }
    public List<Client> getClientsByStatus(Client.ClientStatus status) { return clientRepository.findByStatus(status); }
    public Optional<Client> getClientById(String id) { return clientRepository.findById(id); }
    public Optional<Client> getClientByCode(String code) { return clientRepository.findByCode(code); }
    public Optional<Client> getClientByEmail(String email) { return clientRepository.findByEmail(email); }

    public Client createClient(Client client) {
        client.setId(UUID.randomUUID().toString());
        client.setClientId("CLT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        client.setCreatedAt(new Date());
        client.setUpdatedAt(new Date());
        if (client.getStatus() == null) client.setStatus(Client.ClientStatus.active);
        if (client.getTotalSpent() == null) client.setTotalSpent(0.0);
        if (client.getProjectsCount() == null) client.setProjectsCount(0);
        return clientRepository.save(client);
    }

    public Client updateClient(String id, Client clientDetails) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setName(clientDetails.getName());
        client.setFirstName(clientDetails.getFirstName());
        client.setMiddleName(clientDetails.getMiddleName());
        client.setSurname(clientDetails.getSurname());
        client.setInitials(clientDetails.getInitials());
        client.setIdNumber(clientDetails.getIdNumber());
        client.setDob(clientDetails.getDob());
        client.setEmail(clientDetails.getEmail());
        client.setPhone(clientDetails.getPhone());
        client.setAddress(clientDetails.getAddress());
        client.setWebsite(clientDetails.getWebsite());
        client.setCompanyName(clientDetails.getCompanyName());
        client.setCompanyCK(clientDetails.getCompanyCK());
        client.setStatus(clientDetails.getStatus());
        client.setUpdatedAt(new Date());
        return clientRepository.save(client);
    }

    public void softDeleteClient(String id) {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setDeletedAt(new Date());
        clientRepository.save(client);
    }

    public void restoreClient(String id) {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setDeletedAt(null);
        clientRepository.save(client);
    }

    public void hardDeleteClient(String id) { clientRepository.deleteById(id); }

    public void updateClientStats(String id) {
        // Placeholder
    }

    public List<String> getClientServices(String clientId) {
        return List.of("General Service"); // Placeholder
    }

    public Map<String, Object> getClientStats() {
        List<Client> clients = clientRepository.findAllActive();
        long active = clients.stream().filter(c -> c.getStatus() == Client.ClientStatus.active).count();
        double totalSpent = clients.stream().mapToDouble(c -> c.getTotalSpent() != null ? c.getTotalSpent() : 0.0).sum();
        return Map.of("totalClients", clients.size(), "activeClients", active, "totalSpent", totalSpent);
    }
}
