package com.centoro.api.service;

import com.centoro.api.entities.StaffFile;
import com.centoro.api.repositories.StaffFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StaffFileService {
    
    @Autowired
    private StaffFileRepository staffFileRepository;
    
    @Transactional
    public StaffFile createFile(StaffFile file) {
        file.setFileId("STFDOC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        file.setCreatedAt(new Date());
        file.setUpdatedAt(new Date());
        if (file.getUploadedAt() == null) file.setUploadedAt(new Date());
        return staffFileRepository.save(file);
    }
    
    public List<StaffFile> getAllFiles() {
        return staffFileRepository.findAll();
    }
    
    public List<StaffFile> getFilesByStaff(Long staffId) {
        return staffFileRepository.findByStaffId(staffId);
    }
    
    public List<StaffFile> getFilesByCategory(String category) {
        return staffFileRepository.findByCategory(category);
    }
    
    public Optional<StaffFile> getFileById(Long id) {
        return staffFileRepository.findById(id);
    }
    
    public Optional<StaffFile> getFileByFileId(String fileId) {
        return staffFileRepository.findByFileId(fileId);
    }
    
    @Transactional
    public void deleteFile(Long id) {
        staffFileRepository.deleteById(id);
    }
}