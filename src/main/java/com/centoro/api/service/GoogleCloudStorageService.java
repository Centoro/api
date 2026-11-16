package com.centoro.api.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class GoogleCloudStorageService {

    private static final Logger log = Logger.getLogger(GoogleCloudStorageService.class.getName());

    private final Storage storage;
    private final String bucketName;
    private final boolean enabled;

    // ✅ FIX: Make Storage optional and read an 'enabled' flag
    public GoogleCloudStorageService(
            @Autowired(required = false) Storage storage, 
            @Value("${gcp.bucket.name:}") String bucketName,
            @Value("${gcp.enabled:false}") boolean enabled) {
        this.storage = storage;
        this.bucketName = bucketName;
        this.enabled = enabled;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        // ✅ FIX: Prevent NPE if GCP is disabled or not configured
        if (!enabled || storage == null || bucketName == null || bucketName.isEmpty() || bucketName.startsWith("${")) {
            log.warning("GCS is disabled or not configured. Returning mock URL for: " + file.getOriginalFilename());
            return "https://mock-storage.centoro.local/" + folder + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        }

        String fileName = folder + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setCacheControl("public, max-age=31536000")
                .build();
        storage.create(blobInfo, file.getBytes());
        log.info("File uploaded to GCS: " + fileName);
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public void deleteObject(String folder, String objectName) {
        if (!enabled || storage == null) return;
        storage.delete(BlobId.of(bucketName, folder + "/" + objectName));
        log.info("Deleted object: " + folder + "/" + objectName);
    }

    public byte[] readFile(String objectName) throws IOException {
        if (!enabled || storage == null) throw new IOException("GCS is disabled");
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        if (blob == null) throw new IOException("File not found");
        return blob.getContent();
    }

    public String getContentType(String objectName) {
        if (!enabled || storage == null) return "application/octet-stream";
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        return blob != null ? blob.getContentType() : "application/octet-stream";
    }

    public List<Map<String, Object>> listFiles(String folder) {
        if (!enabled || storage == null) return new ArrayList<>();
        List<Map<String, Object>> files = new ArrayList<>();
        storage.list(bucketName, Storage.BlobListOption.prefix(folder + "/")).iterateAll().forEach(blob -> {
            files.add(Map.of(
                "name", blob.getName(),
                "fileName", blob.getName().replace(folder + "/", ""),
                "size", blob.getSize(),
                "contentType", blob.getContentType(),
                "url", String.format("https://storage.googleapis.com/%s/%s", bucketName, blob.getName())
            ));
        });
        return files;
    }
}
