package com.centoro.api.controller;

import com.centoro.api.entities.StaffFile;
import com.centoro.api.service.StaffFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff/files")
@CrossOrigin(origins = "*")
public class StaffFileController {

    @Autowired
    private StaffFileService staffFileService;

    @GetMapping
    public ResponseEntity<?> getAllFiles() {
        List<StaffFile> files = staffFileService.getAllFiles();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", files
        ));
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getFilesByStaff(@PathVariable Long staffId) {
        List<StaffFile> files = staffFileService.getFilesByStaff(staffId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", files
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getFilesByCategory(@PathVariable String category) {
        List<StaffFile> files = staffFileService.getFilesByCategory(category);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", files
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        Optional<StaffFile> fileOpt = staffFileService.getFileById(id);
        if (fileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", fileOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createFile(@RequestBody StaffFile file) {
        if (file.getStaffId() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Staff ID is required"
            ));
        }
        StaffFile saved = staffFileService.createFile(file);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "File uploaded successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        if (!staffFileService.getFileById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        staffFileService.deleteFile(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "File deleted successfully"
        ));
    }
}