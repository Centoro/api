package com.centoro.api.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class GoogleCloudStorageService {

    @Value("${app.storage.project-id:}")
    private String projectId;

    @Value("${app.storage.bucket:}")
    private String bucketName;

    @Value("${app.storage.enabled:false}")
    private boolean enabled;

    private Storage storage;

    private Storage getStorage() throws IOException {
        if (!enabled) {
            throw new IllegalStateException("Cloud storage is disabled. Set GCS_ENABLED=true on the API.");
        }
        if (bucketName == null || bucketName.isBlank()) {
            throw new IllegalStateException("GCS_BUCKET is not configured.");
        }
        if (storage == null) {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            storage = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build()
                    .getService();
            log.info("Google Cloud Storage initialized for project: {}", projectId);
        }
        return storage;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("File is required");
        String safeFolder = normalizeFolder(folder);
        Storage storage = getStorage();
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String safeOriginalName = originalFilename == null ? "upload" : originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = safeFolder + "/" + UUID.randomUUID() + "-" + safeOriginalName;
        
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        // Upload to bucket
        storage.create(blobInfo, file.getBytes());
        log.info("File uploaded successfully: {}", fileName);
        
        // The bucket is private; serve the object through the authenticated API route.
        return "/api/storage/file?object=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    public List<Map<String, Object>> listFiles(String folder) throws IOException {
        String safeFolder = normalizeFolder(folder);
        Storage storage = getStorage();
        List<Map<String, Object>> files = new ArrayList<>();
        for (Blob blob : storage.list(bucketName,
                Storage.BlobListOption.prefix(safeFolder + "/"),
                Storage.BlobListOption.pageSize(1000)).iterateAll()) {
            if (blob.isDirectory()) continue;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("name", blob.getName());
            item.put("fileName", blob.getName().substring(safeFolder.length() + 1));
            item.put("size", blob.getSize());
            item.put("contentType", blob.getContentType());
            item.put("updated", blob.getUpdateTime());
            item.put("url", "/api/storage/file?object=" + URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8));
            files.add(item);
        }
        return files;
    }

    public void deleteObject(String folder, String objectName) throws IOException {
        String safeFolder = normalizeFolder(folder);
        String safeObject = normalizeObject(objectName);
        if (!safeObject.startsWith(safeFolder + "/")) {
            throw new IllegalArgumentException("Object must belong to the selected folder");
        }
        boolean deleted = getStorage().delete(BlobId.of(bucketName, safeObject));
        if (!deleted) throw new IOException("Object not found");
        log.info("Cloud object deleted: {}", safeObject);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(file, "uploads");
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;
        String blobName = fileUrl.contains("object=") ? fileUrl.substring(fileUrl.indexOf("object=") + 7) : fileUrl.replace("https://storage.googleapis.com/" + bucketName + "/", "");
        try {
            getStorage().delete(BlobId.of(bucketName, blobName));
            log.info("File deleted: {}", blobName);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to delete cloud object", e);
        }
    }

    public byte[] readFile(String objectName) throws IOException {
        String safeObject = normalizeObject(objectName);
        var blob = getStorage().get(BlobId.of(bucketName, safeObject));
        if (blob == null) throw new IOException("Object not found");
        return blob.getContent();
    }

    public String getContentType(String objectName) throws IOException {
        var blob = getStorage().get(BlobId.of(bucketName, normalizeObject(objectName)));
        return blob != null && blob.getContentType() != null ? blob.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    private String normalizeFolder(String folder) {
        String normalized = folder == null ? "uploads" : folder.toLowerCase().replaceAll("[^a-z0-9/-]", "").replaceAll("/+", "/");
        if (normalized.startsWith("/") || normalized.contains("..")) throw new IllegalArgumentException("Invalid storage folder");
        Set<String> allowed = Set.of("applicationsce", "designsce", "fridaymadnessce", "instrumentalsce", "monthlymadnessce", "musicce", "samplepacksce", "samplesce", "videosce", "websitesce", "applications", "designs", "fridaymadness", "instrumentals", "monthlymadness", "music", "samplepacks", "samples", "videos", "websites", "uploads");
        if (!allowed.contains(normalized)) throw new IllegalArgumentException("Unsupported storage folder: " + folder);
        return normalized;
    }

    private String normalizeObject(String objectName) {
        if (objectName == null || objectName.isBlank() || objectName.startsWith("/") || objectName.contains("..")) throw new IllegalArgumentException("Invalid storage object");
        return objectName.replaceAll("[^a-zA-Z0-9._/-]", "");
    }
}