-- Test admin account for API verification (password: CentoroAdmin2026!)
SET @uid = UUID();
SET @sid = UUID();
INSERT INTO users (id, email, password_hash, salt, name, surname, role, status, email_verified, has_completed_entry, password, signup_method)
VALUES (@uid, 'testadmin@centoroenterprise.co.za', '$2a$10$qeXIs15keIWKQrtR1iyJtejtVXfZXaWNcCrHKC24XrnfYO0.ZxbnW', UUID(), 'Test Admin', 'Admin', 'admin', 'active', true, 0, '$2a$10$qeXIs15keIWKQrtR1iyJtejtVXfZXaWNcCrHKC24XrnfYO0.ZxbnW', 'admin');
INSERT INTO staff (id, user_id, username, first_name, surname, last_name, email, role, status, can_reset_passwords, can_approve, password_hash, staff_id)
VALUES (@sid, @uid, 'testadmin', 'Test', 'Admin', 'Admin', 'testadmin@centoroenterprise.co.za', 'admin', 'active', true, true, '$2a$10$qeXIs15keIWKQrtR1iyJtejtVXfZXaWNcCrHKC24XrnfYO0.ZxbnW', 'STF-TEST');