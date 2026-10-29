package com.centoro.api.service;

import com.centoro.api.entities.StaffFile;
import com.centoro.api.repositories.StaffFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StaffFileService {

    @Autowired
    private StaffFileRepository staffFileRepository;

    public List<StaffFile> getAllFiles() {
        return staffFileRepository.findAll();
    }

    // ✅ FIX: This correctly uses String to match the Staff entity
    public List<StaffFile> getFilesByStaff(String staffId) {
        return staffFileRepository.findByStaffId(staffId);
    }

    public List<StaffFile> getFilesByCategory(String category) {
        return staffFileRepository.findByCategory(category);
    }

    public Optional<StaffFile> getFileById(Long id) {
        return staffFileRepository.findById(id);
    }

    public StaffFile createFile(StaffFile file) {
        file.setFileId("FILE-" + System.currentTimeMillis());
        file.setCreatedAt(new Date());
        file.setUpdatedAt(new Date());
        return staffFileRepository.save(file);
    }

    public void deleteFile(Long id) {
        staffFileRepository.deleteById(id);
    }
}
