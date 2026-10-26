package com.centoro.api.controller;

import com.centoro.api.service.GoogleCloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class UploadController {

    private final GoogleCloudStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = storageService.uploadFile(file, "uploads");
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Upload successful");
            response.put("url", fileUrl);
            response.put("fileName", file.getOriginalFilename());
            response.put("size", file.getSize());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Upload failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/upload/{folder}")
    public ResponseEntity<Map<String, Object>> uploadFileToFolder(
            @PathVariable String folder,
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = storageService.uploadFile(file, folder);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Upload successful");
            response.put("url", fileUrl);
            response.put("fileName", file.getOriginalFilename());
            response.put("folder", folder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Upload failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/objects/{folder}")
    public ResponseEntity<Map<String, Object>> listFiles(@PathVariable String folder) {
        try {
            List<Map<String, Object>> files = storageService.listFiles(folder);
            return ResponseEntity.ok(Map.of("success", true, "folder", folder, "data", files));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Unable to list bucket objects"));
        }
    }

    @DeleteMapping("/objects/{folder}")
    public ResponseEntity<Map<String, Object>> deleteFile(
            @PathVariable String folder,
            @RequestParam("object") String objectName) {
        try {
            storageService.deleteObject(folder, objectName);
            return ResponseEntity.ok(Map.of("success", true, "message", "Object deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> readFile(@RequestParam("object") String objectName) {
        try {
            objectName = URLDecoder.decode(objectName, StandardCharsets.UTF_8);
            byte[] content = storageService.readFile(objectName);
            String contentType = storageService.getContentType(objectName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(content);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}