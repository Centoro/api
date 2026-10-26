package com.centoro.api.repositories;

import com.centoro.api.entities.SocialPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SocialPostRepository extends JpaRepository<SocialPost, Long> {
    List<SocialPost> findByOrderByTimeDesc();
}