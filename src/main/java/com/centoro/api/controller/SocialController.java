package com.centoro.api.controller;

import com.centoro.api.entities.SocialPost;
import com.centoro.api.repositories.SocialPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/social")
@CrossOrigin(origins = "*")
public class SocialController {

    @Autowired
    private SocialPostRepository socialPostRepository;

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        List<SocialPost> posts = socialPostRepository.findByOrderByTimeDesc();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", posts
        ));
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody SocialPost post) {
        post.setPostId("SPT-" + String.format("%04d", System.currentTimeMillis() % 10000));
        post.setCreatedAt(new Date());
        if (post.getTime() == null) post.setTime(new Date());
        
        SocialPost saved = socialPostRepository.save(post);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Post published successfully"
        ));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (!socialPostRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        socialPostRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Post deleted successfully"
        ));
    }
}