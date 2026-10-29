package com.centoro.api.controller;

import com.centoro.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/clients/stats")
@CrossOrigin(origins = "*")
public class ClientStatsController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<?> getClientStats() {
        Map<String, Object> stats = clientService.getClientStats();
        return ResponseEntity.ok(Map.of("success", true, "data", stats));
    }
}
