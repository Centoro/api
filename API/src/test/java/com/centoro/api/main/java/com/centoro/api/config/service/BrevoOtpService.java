package com.centoro.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Map;
import java.util.UUID;

@Service
public class BrevoOtpService {
    private final JdbcTemplate jdbcTemplate;
    private final String apiKey;
    private final String senderEmail;
    private final String senderName;
    private final String smsSender;
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final SecureRandom secureRandom = new SecureRandom();

    public BrevoOtpService(
            JdbcTemplate jdbcTemplate,
            @Value("${app.brevo.api-key:}") String apiKey,
            @Value("${app.brevo.sender-email:}") String senderEmail,
            @Value("${app.brevo.sender-name:Centoro Enterprise}") String senderName,
            @Value("${app.brevo.sms-sender:}") String smsSender) {
        this.jdbcTemplate = jdbcTemplate;
        this.apiKey = apiKey;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.smsSender = smsSender;
    }

    public String issue(String userId, String channel, String destination) {
        String normalizedChannel = channel == null ? "" : channel.toLowerCase();
        if (!normalizedChannel.equals("email") && !normalizedChannel.equals("sms")) {
            throw new IllegalArgumentException("Choose email or SMS verification");
        }
        if (apiKey == null || apiKey.isBlank()) throw new IllegalStateException("Brevo is not configured on the API");
        if (destination == null || destination.isBlank()) throw new IllegalArgumentException("No verified destination is available for this channel");
        if (normalizedChannel.equals("email") && (senderEmail == null || senderEmail.isBlank())) {
            throw new IllegalStateException("BREVO_SENDER_EMAIL is not configured");
        }
        if (normalizedChannel.equals("sms") && (smsSender == null || smsSender.isBlank())) {
            throw new IllegalStateException("BREVO_SMS_SENDER is not configured");
        }

        jdbcTemplate.update("DELETE FROM auth_otp_challenges WHERE expires_at < UTC_TIMESTAMP() OR consumed_at IS NOT NULL");

        Integer recentChallenges = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM auth_otp_challenges WHERE user_id = ? AND created_at > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 15 MINUTE)",
            Integer.class, userId);
        if (recentChallenges != null && recentChallenges >= 5) {
            throw new IllegalArgumentException("Too many verification codes requested. Try again in 15 minutes.");
        }

        String code = String.format("%06d", secureRandom.nextInt(1_000_000));
        String challengeId = UUID.randomUUID().toString();
        jdbcTemplate.update(
            "INSERT INTO auth_otp_challenges (id, user_id, channel, code_hash, expires_at, attempts, created_at) VALUES (?, ?, ?, ?, DATE_ADD(UTC_TIMESTAMP(), INTERVAL 5 MINUTE), 0, UTC_TIMESTAMP())",
            challengeId, userId, normalizedChannel, hash(challengeId + ":" + code));
        try {
            if (normalizedChannel.equals("email")) sendEmail(destination, code);
            else sendSms(destination, code);
        } catch (RuntimeException error) {
            jdbcTemplate.update("DELETE FROM auth_otp_challenges WHERE id = ?", challengeId);
            throw error;
        }
        return challengeId;
    }

    public String verify(String challengeId, String code) {
        if (challengeId == null || code == null || !code.matches("\\d{6}")) {
            throw new IllegalArgumentException("Enter the six-digit verification code");
        }
        var challenges = jdbcTemplate.query(
            "SELECT user_id, code_hash, expires_at, attempts FROM auth_otp_challenges WHERE id = ? AND consumed_at IS NULL",
            (rs, row) -> new OtpChallenge(rs.getString("user_id"), rs.getString("code_hash"), rs.getTimestamp("expires_at"), rs.getInt("attempts")),
            challengeId);
        if (challenges.isEmpty()) throw new IllegalArgumentException("Verification code expired or was already used");
        OtpChallenge challenge = challenges.get(0);
        if (challenge.expiresAt().getTime() <= System.currentTimeMillis()) {
            jdbcTemplate.update("DELETE FROM auth_otp_challenges WHERE id = ?", challengeId);
            throw new IllegalArgumentException("Verification code expired. Sign in again to request a new code.");
        }
        int attempts = challenge.attempts();
        if (attempts >= 5) {
            jdbcTemplate.update("DELETE FROM auth_otp_challenges WHERE id = ?", challengeId);
            throw new IllegalArgumentException("Too many incorrect codes. Sign in again to request a new code.");
        }
        String expected = challenge.codeHash();
        String actual = hash(challengeId + ":" + code);
        if (!MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), actual.getBytes(StandardCharsets.UTF_8))) {
            jdbcTemplate.update("UPDATE auth_otp_challenges SET attempts = attempts + 1 WHERE id = ?", challengeId);
            throw new IllegalArgumentException("Invalid verification code");
        }
        int consumed = jdbcTemplate.update("UPDATE auth_otp_challenges SET consumed_at = UTC_TIMESTAMP() WHERE id = ? AND consumed_at IS NULL AND expires_at > UTC_TIMESTAMP() AND attempts < 5", challengeId);
        if (consumed != 1) throw new IllegalArgumentException("Verification code expired or was already used");
        return challenge.userId();
    }

    private record OtpChallenge(String userId, String codeHash, java.sql.Timestamp expiresAt, int attempts) {}

    @Scheduled(fixedDelay = 3_600_000L)
    public void removeExpiredChallenges() {
        jdbcTemplate.update("DELETE FROM auth_otp_challenges WHERE expires_at < UTC_TIMESTAMP() OR consumed_at IS NOT NULL");
    }

    private void sendEmail(String destination, String code) {
        String json = "{\"sender\":{\"name\":\"" + json(senderName) + "\",\"email\":\"" + json(senderEmail) + "\"},"
            + "\"to\":[{\"email\":\"" + json(destination) + "\"}],"
            + "\"subject\":\"Your Centoro verification code\","
            + "\"textContent\":\"Your Centoro verification code is " + code + ". It expires in 5 minutes.\"}";
        sendBrevo("https://api.brevo.com/v3/smtp/email", json);
    }

    private void sendSms(String destination, String code) {
        String json = "{\"sender\":\"" + json(smsSender) + "\",\"recipient\":\"" + json(destination) + "\","
            + "\"content\":\"Centoro verification code: " + code + ". Expires in 5 minutes.\",\"type\":\"transactional\"}";
        sendBrevo("https://api.brevo.com/v3/transactionalSMS/send", json);
    }

    private void sendBrevo(String endpoint, String body) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint))
                .timeout(Duration.ofSeconds(15))
                .header("api-key", apiKey)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Brevo rejected the OTP delivery (HTTP " + response.statusCode() + ")");
            }
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Brevo delivery was interrupted", error);
        } catch (java.io.IOException error) {
            throw new IllegalStateException("Unable to reach Brevo", error);
        }
    }

    private String hash(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(apiKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return HexFormat.of().formatHex(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception error) {
            throw new IllegalStateException("Unable to protect OTP challenge", error);
        }
    }

    private String json(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}