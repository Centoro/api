CREATE TABLE auth_otp_challenges (
    id CHAR(36) NOT NULL PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    channel VARCHAR(10) NOT NULL,
    code_hash CHAR(64) NOT NULL,
    expires_at DATETIME NOT NULL,
    attempts INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL,
    consumed_at DATETIME NULL,
    INDEX idx_auth_otp_user_created (user_id, created_at),
    INDEX idx_auth_otp_expiry (expires_at)
);