package com.centoro.api.controller;

import com.centoro.api.dto.LoginRequest;
import com.centoro.api.dto.RegisterRequest;
import com.centoro.api.dto.VerifyOtpRequest;
import com.centoro.api.service.BrevoOtpService;
import com.centoro.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BrevoOtpService brevoOtpService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Get the identifier (will be email if provided by frontend, otherwise username)
        String identifier = request.getLoginIdentifier();

        // 2. Check BOTH email and username columns against that single identifier
        var accounts = jdbcTemplate.<Map<String, Object>>query(
            "SELECT u.id, u.email, u.password_hash, u.name, u.role, u.status, s.id AS staff_id, " +
            "s.username, s.first_name, s.surname, s.can_reset_passwords, s.can_approve " +
            ", COALESCE(NULLIF(s.mobile, ''), s.phone) AS otp_phone " +
            "FROM users u LEFT JOIN staff s ON s.user_id = u.id " +
            "WHERE u.email = ? OR s.username = ? LIMIT 1",
            (resultSet, rowNum) -> {
                Map<String, Object> account = new HashMap<String, Object>();
                account.put("id", resultSet.getString("id"));
                account.put("email", resultSet.getString("email"));
                account.put("passwordHash", resultSet.getString("password_hash"));
                account.put("name", resultSet.getString("name"));
                account.put("role", resultSet.getString("role"));
                account.put("status", resultSet.getString("status"));
                account.put("staffId", resultSet.getString("staff_id"));
                account.put("username", resultSet.getString("username"));
                account.put("firstName", resultSet.getString("first_name"));
                account.put("surname", resultSet.getString("surname"));
                account.put("canResetPasswords", resultSet.getBoolean("can_reset_passwords"));
                account.put("canApprove", resultSet.getBoolean("can_approve"));
                account.put("otpPhone", resultSet.getString("otp_phone"));
                return account;
            }, identifier, identifier); // <-- CHANGED: Now uses the smart identifier

        if (accounts.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Invalid credentials"
            ));
        }
        
        Map<String, Object> account = accounts.get(0);
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), (String) account.get("passwordHash"))) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Invalid credentials"
            ));
        }

        if (!"active".equalsIgnoreCase(String.valueOf(account.get("status")))) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Account is not active. Complete verification or contact an administrator."));
        }
        
        // --- NEW CHECK: BYPASS OTP FOR ADMIN ---
        String role = String.valueOf(account.get("role")).toLowerCase();
        if ("admin".equals(role)) {
            String username = (String) account.get("username");
            String token = jwtUtil.generateToken(username != null ? username : (String) account.get("email"));
            
            Map<String, Object> user = new HashMap<>();
            user.put("id", account.get("id")); 
            user.put("staffId", account.get("staffId")); 
            user.put("username", username);
            
            String staffName = ((account.get("firstName") == null ? "" : account.get("firstName")) + " " + (account.get("surname") == null ? "" : account.get("surname"))).trim();
            user.put("name", staffName.isBlank() ? account.get("name") : staffName);
            user.put("email", account.get("email")); 
            user.put("role", account.get("role") != null ? account.get("role") : "admin");
            user.put("canResetPasswords", account.get("canResetPasswords")); 
            user.put("canApprove", account.get("canApprove"));
            
            return ResponseEntity.ok(Map.of("success", true, "data", Map.of("token", token, "user", user)));
        }
        // ----------------------------------------

        String channel = request.getChannel() == null ? "email" : request.getChannel().toLowerCase();
        String destination = "sms".equals(channel) ? (String) account.get("otpPhone") : (String) account.get("email");
        try {
            String challengeId = brevoOtpService.issue((String) account.get("id"), channel, destination);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", Map.of("challengeId", challengeId, "channel", channel, "destination", maskDestination(destination, channel)),
                "message", "Verification code sent"
            ));
        } catch (IllegalArgumentException | IllegalStateException error) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", error.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            String userId = brevoOtpService.verify(request.getChallengeId(), request.getCode());
            var accounts = jdbcTemplate.<Map<String, Object>>query(
                "SELECT u.id, u.email, u.name, u.role, u.status, s.id AS staff_id, s.username, s.first_name, s.surname, s.can_reset_passwords, s.can_approve " +
                "FROM users u LEFT JOIN staff s ON s.user_id = u.id WHERE u.id = ? LIMIT 1",
                (rs, rowNum) -> {
                    Map<String, Object> account = new HashMap<>();
                    account.put("id", rs.getString("id")); account.put("email", rs.getString("email"));
                    account.put("role", rs.getString("role")); account.put("status", rs.getString("status")); account.put("staffId", rs.getString("staff_id"));
                    account.put("name", rs.getString("name"));
                    account.put("username", rs.getString("username")); account.put("firstName", rs.getString("first_name"));
                    account.put("surname", rs.getString("surname")); account.put("canResetPasswords", rs.getBoolean("can_reset_passwords"));
                    account.put("canApprove", rs.getBoolean("can_approve")); return account;
                }, userId);
            if (accounts.isEmpty()) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Account not found"));
            Map<String, Object> account = accounts.get(0);
            jdbcTemplate.update("UPDATE users SET email_verified = true, status = 'active' WHERE id = ?", userId);
            jdbcTemplate.update("UPDATE staff SET status = 'active' WHERE user_id = ?", userId);
            String username = (String) account.get("username");
            String token = jwtUtil.generateToken(username != null ? username : (String) account.get("email"));
            Map<String, Object> user = new HashMap<>();
            user.put("id", account.get("id")); user.put("staffId", account.get("staffId")); user.put("username", username);
            String staffName = ((account.get("firstName") == null ? "" : account.get("firstName")) + " " + (account.get("surname") == null ? "" : account.get("surname"))).trim();
            user.put("name", staffName.isBlank() ? account.get("name") : staffName);
            user.put("email", account.get("email")); user.put("role", account.get("role") != null ? account.get("role") : "staff");
            user.put("canResetPasswords", account.get("canResetPasswords")); user.put("canApprove", account.get("canApprove"));
            return ResponseEntity.ok(Map.of("success", true, "data", Map.of("token", token, "user", user)));
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", error.getMessage()));
        }
    }

    private String maskDestination(String destination, String channel) {
        if (destination == null || destination.isBlank()) return "";
        if ("sms".equals(channel)) return destination.length() < 5 ? "***" : "***" + destination.substring(destination.length() - 4);
        int at = destination.indexOf('@');
        if (at < 1) return "***";
        return destination.substring(0, 1) + "***" + destination.substring(at);
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.getName() == null || request.getName().isBlank() || request.getUsername() == null || request.getUsername().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null || request.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Name, username, email, and a password of at least 8 characters are required"));
        }
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM staff WHERE username = ?", Integer.class, request.getUsername()) > 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Username already exists"
            ));
        }
        
        if (request.getEmail() != null && jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, request.getEmail()) > 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Email already exists"
            ));
        }
        
        String userId = UUID.randomUUID().toString();
        String staffId = UUID.randomUUID().toString();
        String[] nameParts = request.getName().trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String surname = nameParts.length > 1 ? nameParts[1] : "";
        String username = request.getUsername();
        jdbcTemplate.update("INSERT INTO users (id, email, password_hash, salt, name, role, status, email_verified) VALUES (?, ?, ?, ?, ?, 'staff', 'pending', false)",
            userId, request.getEmail(), passwordEncoder.encode(request.getPassword()), UUID.randomUUID().toString(), request.getName());
        jdbcTemplate.update("INSERT INTO staff (id, user_id, username, first_name, surname, email, role, status, can_reset_passwords, can_approve) VALUES (?, ?, ?, ?, ?, ?, 'staff', 'inactive', false, false)",
            staffId, userId, username, firstName, surname, request.getEmail());
        try {
            String challengeId = brevoOtpService.issue(userId, "email", request.getEmail());
            return ResponseEntity.ok(Map.of("success", true,
                "data", Map.of("challengeId", challengeId, "channel", "email", "destination", maskDestination(request.getEmail(), "email"), "registration", true),
                "message", "Verification code sent"));
        } catch (IllegalArgumentException | IllegalStateException error) {
            throw error;
        }
    }
}
