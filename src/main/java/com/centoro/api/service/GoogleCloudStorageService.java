package com.centoro.api.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j; // Make sure lombok is in your pom.xml
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j // FIX: Added back so log.info works
@Service
public class GoogleCloudStorageService {

    private final Storage storage;
    private final String bucketName;

    public GoogleCloudStorageService(Storage storage, @Value("${gcp.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String fileName = folder + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setCacheControl("public, max-age=31536000")
                .build();
        storage.create(blobInfo, file.getBytes());
        log.info("File uploaded to GCS: {}", fileName); // FIX: log works now
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public void deleteObject(String folder, String objectName) {
        storage.delete(BlobId.of(bucketName, folder + "/" + objectName));
        log.info("Deleted object: {}/{}", folder, objectName);
    }

    public byte[] readFile(String objectName) throws IOException {
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        if (blob == null) throw new IOException("File not found");
        return blob.getContent();
    }

    public String getContentType(String objectName) {
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        return blob != null ? blob.getContentType() : "application/octet-stream";
    }

    public List<Map<String, Object>> listFiles(String folder) {
        List<Map<String, Object>> files = new ArrayList<>();
        storage.list(bucketName, Storage.BlobListOption.prefix(folder + "/")).iterateAll().forEach(blob -> {
            files.add(Map.of("name", blob.getName(), "fileName", blob.getName().replace(folder + "/", ""), "size", blob.getSize(), "contentType", blob.getContentType(), "url", String.format("https://storage.googleapis.com/%s/%s", bucketName, blob.getName())));
        });
        return files;
    }
}
