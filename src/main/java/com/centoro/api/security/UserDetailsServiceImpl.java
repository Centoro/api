package com.centoro.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        var accounts = jdbcTemplate.query(
            "SELECT u.email, u.password_hash, u.role, s.username " +
            "FROM users u LEFT JOIN staff s ON s.user_id = u.id " +
            "WHERE u.email = ? OR s.username = ? LIMIT 1",
            (resultSet, rowNum) -> new String[] {
                resultSet.getString("email"), resultSet.getString("password_hash"),
                resultSet.getString("role"), resultSet.getString("username")
            }, identifier, identifier);
        if (accounts.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username or email: " + identifier);
        }
        String[] account = accounts.get(0);
        String loginName = account[3] != null ? account[3] : account[0];
        String role = account[2] != null ? account[2].toUpperCase() : "STAFF";

        return new User(
            loginName,
            account[1] != null ? account[1] : "",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}