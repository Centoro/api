package com.centoro.api.controller;

import com.centoro.api.entities.User;
import com.centoro.api.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping 
    public List<User> getAllUsers() { return userRepository.findAll(); }
    
    @GetMapping("/{id}") 
    public ResponseEntity<User> getUserById(@PathVariable String id) { // FIX: String
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping 
    public User createUser(@RequestBody User user) { return userRepository.save(user); }
}
