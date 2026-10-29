package com.centoro.api.controller;

import com.centoro.api.entities.Client;
import com.centoro.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(Map.of("success", true, "data", clients));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllClientsWithDeleted() {
        List<Client> clients = clientService.getAllClientsWithDeleted();
        return ResponseEntity.ok(Map.of("success", true, "data", clients));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchClients(@RequestParam String q) {
        List<Client> clients = clientService.searchClients(q);
        return ResponseEntity.ok(Map.of("success", true, "data", clients));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getClientsByStatus(@PathVariable String status) {
        Client.ClientStatus clientStatus;
        try {
            clientStatus = Client.ClientStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid status value"));
        }
        List<Client> clients = clientService.getClientsByStatus(clientStatus);
        return ResponseEntity.ok(Map.of("success", true, "data", clients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable String id) { // FIX: String
        Optional<Client> client = clientService.getClientById(id);
        if (client.isEmpty() || client.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", client.get()));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getClientByCode(@PathVariable String code) {
        Optional<Client> client = clientService.getClientByCode(code);
        if (client.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", client.get()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.getClientByEmail(email);
        if (client.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", client.get()));
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        Client saved = clientService.createClient(client);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Client created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable String id, @RequestBody Client client) { // FIX: String
        try {
            Client updated = clientService.updateClient(id, client);
            return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Client updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable String id) { // FIX: String
        try {
            clientService.softDeleteClient(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Client moved to bin successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
