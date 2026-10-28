package com.centoro.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This annotation ensures the API won't crash if the frontend sends extra fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    
    private String username;
    private String email;      // <-- ADDED: To support email login
    private String password;
    private String channel;

    // Getters and Setters
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getChannel() { 
        return channel; 
    }
    public void setChannel(String channel) { 
        this.channel = channel; 
    }

    /**
     * Helper method: Returns the email if provided, otherwise falls back to username.
     * Use this in your AuthService to look up the user in the database.
     */
    public String getLoginIdentifier() {
        if (email != null && !email.trim().isEmpty()) {
            return email.trim();
        }
        return username != null ? username.trim() : "";
    }
}
