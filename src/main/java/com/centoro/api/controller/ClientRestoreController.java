package com.centoro.api.controller;

import com.centoro.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientRestoreController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/{id}/restore")
    public ResponseEntity<?> restoreClient(@PathVariable String id) {
        try {
            clientService.restoreClient(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Client restored successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<?> hardDeleteClient(@PathVariable String id) {
        try {
            clientService.hardDeleteClient(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Client permanently deleted"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
