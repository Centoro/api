package com.centoro.api.controller;

import com.centoro.api.entities.StaffGroup;
import com.centoro.api.entities.StaffNotification;
import com.centoro.api.service.StaffGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff-groups")
@CrossOrigin(origins = "*")
public class StaffGroupController {

    @Autowired
    private StaffGroupService staffGroupService;

    // ============ GROUPS ============
    
    @GetMapping
    public ResponseEntity<?> getAllGroups() {
        List<StaffGroup> groups = staffGroupService.getAllGroups();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", groups
        ));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getGroupsByType(@PathVariable String type) {
        List<StaffGroup> groups = staffGroupService.getGroupsByType(type);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", groups
        ));
    }

    @GetMapping("/member/{staffId}")
    public ResponseEntity<?> getGroupsByMember(@PathVariable String staffId) {
        List<StaffGroup> groups = staffGroupService.getGroupsByMember(staffId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", groups
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<StaffGroup> groupOpt = staffGroupService.getGroupById(id);
        if (groupOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", groupOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody StaffGroup group) {
        StaffGroup saved = staffGroupService.createGroup(group);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Group created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody StaffGroup group) {
        try {
            StaffGroup updated = staffGroupService.updateGroup(id, group);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", updated,
                "message", "Group updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<?> addMember(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String staffId = request.get("staffId");
        if (staffId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "staffId is required"
            ));
        }
        try {
            StaffGroup updated = staffGroupService.addMember(id, staffId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", updated,
                "message", "Member added successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/members")
    public ResponseEntity<?> removeMember(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String staffId = request.get("staffId");
        if (staffId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "staffId is required"
            ));
        }
        try {
            StaffGroup updated = staffGroupService.removeMember(id, staffId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", updated,
                "message", "Member removed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        if (!staffGroupService.getGroupById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        staffGroupService.deleteGroup(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Group deleted successfully"
        ));
    }

    // ============ NOTIFICATIONS ============
    
    @GetMapping("/notifications")
    public ResponseEntity<?> getAllNotifications() {
        List<StaffNotification> notifications = staffGroupService.getAllNotifications();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", notifications
        ));
    }

    @GetMapping("/notifications/global")
    public ResponseEntity<?> getGlobalNotifications() {
        List<StaffNotification> notifications = staffGroupService.getGlobalNotifications();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", notifications
        ));
    }

    @GetMapping("/notifications/department/{department}")
    public ResponseEntity<?> getNotificationsByDepartment(@PathVariable String department) {
        List<StaffNotification> notifications = staffGroupService.getNotificationsByDepartment(department);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", notifications
        ));
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<?> getNotification(@PathVariable Long id) {
        Optional<StaffNotification> notificationOpt = staffGroupService.getNotificationById(id);
        if (notificationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", notificationOpt.get()
        ));
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> createNotification(@RequestBody StaffNotification notification) {
        StaffNotification saved = staffGroupService.createNotification(notification);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Notification sent successfully"
        ));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        if (!staffGroupService.getNotificationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        staffGroupService.deleteNotification(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Notification deleted successfully"
        ));
    }
}