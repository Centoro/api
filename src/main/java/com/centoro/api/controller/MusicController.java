package com.centoro.api.controller;

import com.centoro.api.entities.MusicTrack;
import com.centoro.api.entities.MusicPack;
import com.centoro.api.entities.MusicSample;
import com.centoro.api.repositories.MusicTrackRepository;
import com.centoro.api.repositories.MusicPackRepository;
import com.centoro.api.repositories.MusicSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/music")
@CrossOrigin(origins = "*")
public class MusicController {

    @Autowired
    private MusicTrackRepository trackRepository;
    
    @Autowired
    private MusicPackRepository packRepository;
    
    @Autowired
    private MusicSampleRepository sampleRepository;

    // ============ TRACKS ============
    
    @GetMapping("/tracks")
    public ResponseEntity<?> getAllTracks() {
        List<MusicTrack> tracks = trackRepository.findAll();
        tracks.removeIf(t -> t.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", tracks
        ));
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<?> getTrack(@PathVariable Long id) {
        Optional<MusicTrack> track = trackRepository.findById(id);
        if (track.isEmpty() || track.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", track.get()
        ));
    }

    @GetMapping("/tracks/genre/{genre}")
    public ResponseEntity<?> getTracksByGenre(@PathVariable String genre) {
        List<MusicTrack> tracks = trackRepository.findByGenre(genre);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", tracks
        ));
    }

    @PostMapping("/tracks")
    public ResponseEntity<?> createTrack(@RequestBody MusicTrack track) {
        track.setTrackId("TRK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        track.setCreatedAt(new Date());
        track.setUpdatedAt(new Date());
        
        if (track.getLeasePrice() == null) track.setLeasePrice(450.0);
        if (track.getExclusivePrice() == null) track.setExclusivePrice(2500.0);
        if (track.getRoyaltyFreePrice() == null) track.setRoyaltyFreePrice(1200.0);
        
        MusicTrack saved = trackRepository.save(track);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Track created successfully"
        ));
    }

    @PutMapping("/tracks/{id}")
    public ResponseEntity<?> updateTrack(@PathVariable Long id, @RequestBody MusicTrack track) {
        Optional<MusicTrack> existingOpt = trackRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        MusicTrack existing = existingOpt.get();
        existing.setTitle(track.getTitle());
        existing.setGenre(track.getGenre());
        existing.setBpm(track.getBpm());
        existing.setKey(track.getKey());
        existing.setLeasePrice(track.getLeasePrice());
        existing.setRoyaltyFreePrice(track.getRoyaltyFreePrice());
        existing.setExclusivePrice(track.getExclusivePrice());
        existing.setCoverUrl(track.getCoverUrl());
        existing.setAudioUrl(track.getAudioUrl());
        existing.setUpdatedAt(new Date());
        
        MusicTrack updated = trackRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Track updated successfully"
        ));
    }

    @DeleteMapping("/tracks/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
        Optional<MusicTrack> trackOpt = trackRepository.findById(id);
        if (trackOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        MusicTrack track = trackOpt.get();
        track.setDeletedAt(new Date());
        trackRepository.save(track);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Track deleted successfully"
        ));
    }

    // ============ PACKS ============
    
    @GetMapping("/packs")
    public ResponseEntity<?> getAllPacks() {
        List<MusicPack> packs = packRepository.findAll();
        packs.removeIf(p -> p.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", packs
        ));
    }

    @GetMapping("/packs/{id}")
    public ResponseEntity<?> getPack(@PathVariable Long id) {
        Optional<MusicPack> pack = packRepository.findById(id);
        if (pack.isEmpty() || pack.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", pack.get()
        ));
    }

    @PostMapping("/packs")
    public ResponseEntity<?> createPack(@RequestBody MusicPack pack) {
        pack.setPackId("PCK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        pack.setCreatedAt(new Date());
        pack.setUpdatedAt(new Date());
        
        if (pack.getLeasePrice() == null) pack.setLeasePrice(450.0);
        if (pack.getExclusivePrice() == null) pack.setExclusivePrice(2500.0);
        
        MusicPack saved = packRepository.save(pack);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Pack created successfully"
        ));
    }

    @PutMapping("/packs/{id}")
    public ResponseEntity<?> updatePack(@PathVariable Long id, @RequestBody MusicPack pack) {
        Optional<MusicPack> existingOpt = packRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        MusicPack existing = existingOpt.get();
        existing.setTitle(pack.getTitle());
        existing.setGenre(pack.getGenre());
        existing.setLeasePrice(pack.getLeasePrice());
        existing.setExclusivePrice(pack.getExclusivePrice());
        existing.setCoverUrl(pack.getCoverUrl());
        existing.setAudioUrl(pack.getAudioUrl());
        existing.setSampleList(pack.getSampleList());
        existing.setUpdatedAt(new Date());
        
        MusicPack updated = packRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Pack updated successfully"
        ));
    }

    @DeleteMapping("/packs/{id}")
    public ResponseEntity<?> deletePack(@PathVariable Long id) {
        Optional<MusicPack> packOpt = packRepository.findById(id);
        if (packOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        MusicPack pack = packOpt.get();
        pack.setDeletedAt(new Date());
        packRepository.save(pack);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Pack deleted successfully"
        ));
    }

    // ============ SAMPLES ============
    
    @GetMapping("/samples")
    public ResponseEntity<?> getAllSamples() {
        List<MusicSample> samples = sampleRepository.findAll();
        samples.removeIf(s -> s.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", samples
        ));
    }

    @GetMapping("/samples/{id}")
    public ResponseEntity<?> getSample(@PathVariable Long id) {
        Optional<MusicSample> sample = sampleRepository.findById(id);
        if (sample.isEmpty() || sample.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", sample.get()
        ));
    }

    @PostMapping("/samples")
    public ResponseEntity<?> createSample(@RequestBody MusicSample sample) {
        sample.setSampleId("SMP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        sample.setCreatedAt(new Date());
        sample.setUpdatedAt(new Date());
        
        if (sample.getPrice() == null) sample.setPrice(150.0);
        
        MusicSample saved = sampleRepository.save(sample);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Sample created successfully"
        ));
    }

    @PutMapping("/samples/{id}")
    public ResponseEntity<?> updateSample(@PathVariable Long id, @RequestBody MusicSample sample) {
        Optional<MusicSample> existingOpt = sampleRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        MusicSample existing = existingOpt.get();
        existing.setTitle(sample.getTitle());
        existing.setCategory(sample.getCategory());
        existing.setGenre(sample.getGenre());
        existing.setBpm(sample.getBpm());
        existing.setPrice(sample.getPrice());
        existing.setCoverUrl(sample.getCoverUrl());
        existing.setAudioUrl(sample.getAudioUrl());
        existing.setUpdatedAt(new Date());
        
        MusicSample updated = sampleRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Sample updated successfully"
        ));
    }

    @DeleteMapping("/samples/{id}")
    public ResponseEntity<?> deleteSample(@PathVariable Long id) {
        Optional<MusicSample> sampleOpt = sampleRepository.findById(id);
        if (sampleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        MusicSample sample = sampleOpt.get();
        sample.setDeletedAt(new Date());
        sampleRepository.save(sample);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Sample deleted successfully"
        ));
    }
}