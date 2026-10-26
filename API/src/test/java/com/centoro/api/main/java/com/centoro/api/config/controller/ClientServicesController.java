package com.centoro.api.controller;

import com.centoro.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientServicesController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}/services")
    public ResponseEntity<?> getClientServices(@PathVariable Long id) {
        if (!clientService.getClientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        List<String> services = clientService.getClientServices(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", services
        ));
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<?> getClientProjects(@PathVariable Long id) {
        if (!clientService.getClientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // This reuses the existing ProjectRepository
        // ProjectsController already handles this via /api/projects/client/{clientId}
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Use /api/projects/client/" + id + " endpoint"
        ));
    }

    @GetMapping("/{id}/invoices")
    public ResponseEntity<?> getClientInvoices(@PathVariable Long id) {
        if (!clientService.getClientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // This reuses the existing InvoiceRepository
        // InvoicesController already handles this via /api/invoices/client/{clientId}
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Use /api/invoices/client/" + id + " endpoint"
        ));
    }

    @PostMapping("/{id}/recalculate")
    public ResponseEntity<?> recalculateClientStats(@PathVariable Long id) {
        try {
            clientService.updateClientStats(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Client stats recalculated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}