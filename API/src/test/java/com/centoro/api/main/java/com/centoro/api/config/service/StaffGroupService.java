package com.centoro.api.service;

import com.centoro.api.entities.StaffGroup;
import com.centoro.api.entities.StaffNotification;
import com.centoro.api.repositories.StaffGroupRepository;
import com.centoro.api.repositories.StaffNotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class StaffGroupService {
    
    @Autowired
    private StaffGroupRepository staffGroupRepository;
    
    @Autowired
    private StaffNotificationRepository staffNotificationRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // ============ GROUPS ============
    
    @Transactional
    public StaffGroup createGroup(StaffGroup group) {
        group.setGroupId("SGR-" + String.format("%04d", System.currentTimeMillis() % 10000));
        group.setCreatedAt(new Date());
        group.setUpdatedAt(new Date());
        return staffGroupRepository.save(group);
    }
    
    public List<StaffGroup> getAllGroups() {
        return staffGroupRepository.findAll();
    }
    
    public List<StaffGroup> getGroupsByType(String type) {
        return staffGroupRepository.findByType(type);
    }
    
    public Optional<StaffGroup> getGroupById(Long id) {
        return staffGroupRepository.findById(id);
    }
    
    public Optional<StaffGroup> getGroupByGroupId(String groupId) {
        return staffGroupRepository.findByGroupId(groupId);
    }
    
    public List<StaffGroup> getGroupsByMember(String staffId) {
        return staffGroupRepository.findByMemberId(staffId);
    }
    
    @Transactional
    public StaffGroup updateGroup(Long id, StaffGroup group) {
        StaffGroup existing = staffGroupRepository.findById(id).orElseThrow();
        existing.setName(group.getName());
        existing.setType(group.getType());
        existing.setDescription(group.getDescription());
        existing.setMembers(group.getMembers());
        existing.setUpdatedAt(new Date());
        return staffGroupRepository.save(existing);
    }
    
    @Transactional
    public void deleteGroup(Long id) {
        staffGroupRepository.deleteById(id);
    }
    
    @Transactional
    public StaffGroup addMember(Long id, String staffId) {
        StaffGroup group = staffGroupRepository.findById(id).orElseThrow();
        List<String> members = parseMembers(group.getMembers());
        if (!members.contains(staffId)) {
            members.add(staffId);
            group.setMembers(toJson(members));
            group.setUpdatedAt(new Date());
            return staffGroupRepository.save(group);
        }
        return group;
    }
    
    @Transactional
    public StaffGroup removeMember(Long id, String staffId) {
        StaffGroup group = staffGroupRepository.findById(id).orElseThrow();
        List<String> members = parseMembers(group.getMembers());
        members.remove(staffId);
        group.setMembers(toJson(members));
        group.setUpdatedAt(new Date());
        return staffGroupRepository.save(group);
    }
    
    // ============ NOTIFICATIONS ============
    
    @Transactional
    public StaffNotification createNotification(StaffNotification notification) {
        notification.setNotificationId("NTF-" + String.format("%04d", System.currentTimeMillis() % 10000));
        notification.setCreatedAt(new Date());
        if (notification.getTime() == null) notification.setTime(new Date());
        return staffNotificationRepository.save(notification);
    }
    
    public List<StaffNotification> getAllNotifications() {
        return staffNotificationRepository.findByOrderByTimeDesc();
    }
    
    public List<StaffNotification> getGlobalNotifications() {
        return staffNotificationRepository.findGlobalNotifications();
    }
    
    public List<StaffNotification> getNotificationsByDepartment(String department) {
        return staffNotificationRepository.findByDepartment(department);
    }
    
    public Optional<StaffNotification> getNotificationById(Long id) {
        return staffNotificationRepository.findById(id);
    }
    
    @Transactional
    public void deleteNotification(Long id) {
        staffNotificationRepository.deleteById(id);
    }
    
    // ============ HELPERS ============
    
    private List<String> parseMembers(String membersJson) {
        if (membersJson == null || membersJson.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(membersJson, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    private String toJson(List<String> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return "[]";
        }
    }
}