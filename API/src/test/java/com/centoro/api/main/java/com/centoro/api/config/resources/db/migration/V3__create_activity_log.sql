CREATE TABLE IF NOT EXISTS activity_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    activity_id VARCHAR(50) NOT NULL,
    user_id VARCHAR(50),
    action VARCHAR(255) NOT NULL,
    icon VARCHAR(50),
    color VARCHAR(20),
    created_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_activity_log_activity_id (activity_id),
    INDEX ix_activity_log_user_id (user_id),
    INDEX ix_activity_log_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
