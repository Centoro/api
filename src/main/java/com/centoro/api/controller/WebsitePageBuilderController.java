package com.centoro.api.controller;

import com.centoro.api.entities.WebsitePage;
import com.centoro.api.repositories.WebsitePageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/website/builder")
@CrossOrigin(origins = "*")
public class WebsitePageBuilderController {

    @Autowired
    private WebsitePageRepository websitePageRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    // ============ PAGES ============
    
    @GetMapping("/pages")
    public ResponseEntity<?> getAllPages() {
        List<WebsitePage> pages = websitePageRepository.findAll();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", pages
        ));
    }

    @GetMapping("/pages/main")
    public ResponseEntity<?> getMainPage() {
        List<WebsitePage> pages = websitePageRepository.findByIsMainTrue();
        WebsitePage page = pages.isEmpty() ? null : pages.get(0);
        if (page == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", page
        ));
    }

    @GetMapping("/pages/{id}")
    public ResponseEntity<?> getPage(@PathVariable Long id) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", pageOpt.get()
        ));
    }

    @GetMapping("/pages/slug/{slug}")
    public ResponseEntity<?> getPageBySlug(@PathVariable String slug) {
        WebsitePage page = websitePageRepository.findBySlug(slug);
        if (page == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", page
        ));
    }

    @PostMapping("/pages")
    public ResponseEntity<?> createPage(@RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String template = (String) request.get("template");
        
        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Title is required"
            ));
        }
        
        WebsitePage page = new WebsitePage();
        page.setPageId("PAGE-" + String.format("%04d", System.currentTimeMillis() % 10000));
        page.setTitle(title);
        page.setSlug(title.toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        page.setTemplate(template != null ? template : "empty");
        page.setIsMain(false);
        page.setIsPublished(false);
        page.setCreatedAt(new Date());
        page.setUpdatedAt(new Date());
        
        // Set default sections based on template
        Map<String, Object> defaultSections = getTemplateSections(template);
        try {
            page.setSections(objectMapper.writeValueAsString(defaultSections.get("sections")));
            page.setSettings(objectMapper.writeValueAsString(defaultSections.get("settings")));
        } catch (Exception e) {
            page.setSections("[]");
            page.setSettings("{}");
        }
        
        WebsitePage saved = websitePageRepository.save(page);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Page created successfully"
        ));
    }

    @PutMapping("/pages/{id}")
    public ResponseEntity<?> updatePage(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePage page = pageOpt.get();
        
        if (request.containsKey("title")) {
            String title = (String) request.get("title");
            page.setTitle(title);
            page.setSlug(title.toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        }
        if (request.containsKey("sections")) {
            try {
                page.setSections(objectMapper.writeValueAsString(request.get("sections")));
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid sections data"
                ));
            }
        }
        if (request.containsKey("settings")) {
            try {
                page.setSettings(objectMapper.writeValueAsString(request.get("settings")));
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid settings data"
                ));
            }
        }
        if (request.containsKey("isPublished")) {
            page.setIsPublished((Boolean) request.get("isPublished"));
            if (page.getIsPublished()) {
                page.setPublishedAt(new Date());
            }
        }
        if (request.containsKey("isMain")) {
            // If setting as main, unset other main pages
            if ((Boolean) request.get("isMain")) {
                List<WebsitePage> currentMainList = websitePageRepository.findByIsMainTrue();
                for (WebsitePage currentMain : currentMainList) {
                    if (!currentMain.getId().equals(id)) {
                        currentMain.setIsMain(false);
                        websitePageRepository.save(currentMain);
                    }
                }
                page.setIsMain(true);
            } else {
                page.setIsMain(false);
            }
        }
        
        page.setUpdatedAt(new Date());
        WebsitePage updated = websitePageRepository.save(page);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Page updated successfully"
        ));
    }

    @PostMapping("/pages/{id}/publish")
    public ResponseEntity<?> publishPage(@PathVariable Long id) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePage page = pageOpt.get();
        page.setIsPublished(true);
        page.setPublishedAt(new Date());
        page.setUpdatedAt(new Date());
        
        WebsitePage updated = websitePageRepository.save(page);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Page published successfully"
        ));
    }

    @PostMapping("/pages/{id}/unpublish")
    public ResponseEntity<?> unpublishPage(@PathVariable Long id) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePage page = pageOpt.get();
        page.setIsPublished(false);
        page.setUpdatedAt(new Date());
        
        WebsitePage updated = websitePageRepository.save(page);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Page unpublished successfully"
        ));
    }

    @DeleteMapping("/pages/{id}")
    public ResponseEntity<?> deletePage(@PathVariable Long id) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        websitePageRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Page deleted successfully"
        ));
    }

    // ============ HELPER: TEMPLATES ============
    
    private Map<String, Object> getTemplateSections(String template) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> sections = new ArrayList<>();
        Map<String, Object> settings = new HashMap<>();
        
        settings.put("globalBg", "#ffffff");
        settings.put("globalText", "#1a1a1a");
        settings.put("globalAccent", "#f59e0b");
        settings.put("stickyMenu", true);
        
        if ("business".equals(template)) {
            // Business template sections
            Map<String, Object> hero = createSection("hero");
            hero.put("settings", Map.of(
                "background", "#0f172a",
                "textColor", "#ffffff",
                "padding", "60px 20px",
                "alignment", "center"
            ));
            hero.put("elements", List.of(
                createElement("heading", "Welcome to Centoro Enterprise", 
                    Map.of("fontSize", "42px", "fontWeight", "800", "color", "#ffffff", "textAlign", "center"), 0),
                createElement("text", "Empowering Excellence. Defining Success.", 
                    Map.of("fontSize", "18px", "color", "#94a3b8", "textAlign", "center", "marginTop", "8px"), 1),
                createElement("button", "Get Started", 
                    Map.of("background", "#f59e0b", "color", "#0f172a", "padding", "12px 32px", "borderRadius", "30px", "fontSize", "16px", "fontWeight", "600"), 2)
            ));
            sections.add(hero);
            
            Map<String, Object> features = createSection("features");
            features.put("settings", Map.of(
                "background", "#ffffff",
                "textColor", "#1a1a1a",
                "padding", "40px 20px"
            ));
            features.put("elements", List.of(
                createElement("heading", "Our Services", 
                    Map.of("fontSize", "28px", "fontWeight", "700", "color", "#1a1a1a", "textAlign", "center"), 0),
                createElement("text", "We provide creative and technology solutions for modern businesses.", 
                    Map.of("fontSize", "16px", "color", "#555", "textAlign", "center", "marginTop", "8px"), 1)
            ));
            sections.add(features);
            
        } else if ("music".equals(template)) {
            // Music template
            Map<String, Object> hero = createSection("hero");
            hero.put("settings", Map.of(
                "background", "#0a0a0a",
                "textColor", "#ffffff",
                "padding", "40px 20px",
                "alignment", "center"
            ));
            hero.put("elements", List.of(
                createElement("heading", "🎵 Artist Name", 
                    Map.of("fontSize", "44px", "fontWeight", "800", "color", "#ffffff", "textAlign", "center"), 0),
                createElement("text", "Producer • Beat Maker • Recording Artist", 
                    Map.of("fontSize", "18px", "color", "#f59e0b", "textAlign", "center", "marginTop", "8px"), 1),
                createElement("social", "Connect with me", 
                    Map.of("fontSize", "16px", "color", "#ffffff", "textAlign", "center", "marginTop", "16px"), 2)
            ));
            sections.add(hero);
            
            Map<String, Object> store = createSection("music-store");
            store.put("settings", Map.of(
                "background", "#1a1a1a",
                "textColor", "#ffffff",
                "padding", "40px 20px"
            ));
            store.put("elements", List.of(
                createElement("heading", "🎶 Music Store", 
                    Map.of("fontSize", "28px", "fontWeight", "700", "color", "#f59e0b", "textAlign", "center"), 0),
                createElement("music-store", "Music Store", Map.of(), 1)
            ));
            sections.add(store);
            
        } else if ("portfolio".equals(template)) {
            // Portfolio template
            Map<String, Object> hero = createSection("hero");
            hero.put("settings", Map.of(
                "background", "#0f172a",
                "textColor", "#ffffff",
                "padding", "40px 20px",
                "alignment", "center"
            ));
            hero.put("elements", List.of(
                createElement("heading", "My Creative Portfolio", 
                    Map.of("fontSize", "38px", "fontWeight", "800", "color", "#ffffff", "textAlign", "center"), 0),
                createElement("text", "Showcasing my best work in design, development, and music.", 
                    Map.of("fontSize", "18px", "color", "#94a3b8", "textAlign", "center", "marginTop", "8px"), 1)
            ));
            sections.add(hero);
            
            Map<String, Object> gallery = createSection("gallery");
            gallery.put("settings", Map.of(
                "background", "#f8fafc",
                "textColor", "#1a1a1a",
                "padding", "40px 20px"
            ));
            gallery.put("elements", List.of(
                createElement("heading", "Featured Work", 
                    Map.of("fontSize", "28px", "fontWeight", "700", "color", "#1a1a1a", "textAlign", "center"), 0)
            ));
            sections.add(gallery);
            
        } else if ("landing".equals(template)) {
            // Landing page template
            Map<String, Object> hero = createSection("hero");
            hero.put("settings", Map.of(
                "background", "linear-gradient(135deg, #0f172a, #1e293b)",
                "textColor", "#ffffff",
                "padding", "80px 20px",
                "alignment", "center"
            ));
            hero.put("elements", List.of(
                createElement("heading", "The Ultimate Solution", 
                    Map.of("fontSize", "48px", "fontWeight", "800", "color", "#ffffff", "textAlign", "center"), 0),
                createElement("text", "Join thousands of satisfied customers. Get started today.", 
                    Map.of("fontSize", "20px", "color", "#94a3b8", "textAlign", "center", "marginTop", "12px"), 1),
                createElement("button", "Sign Up Now →", 
                    Map.of("background", "#f59e0b", "color", "#0f172a", "padding", "14px 40px", "borderRadius", "30px", "fontSize", "18px", "fontWeight", "700"), 2)
            ));
            sections.add(hero);
            
        } else if ("blog".equals(template)) {
            // Blog template
            Map<String, Object> hero = createSection("hero");
            hero.put("settings", Map.of(
                "background", "#f8fafc",
                "textColor", "#1a1a1a",
                "padding", "40px 20px",
                "alignment", "center"
            ));
            hero.put("elements", List.of(
                createElement("heading", "📝 Blog", 
                    Map.of("fontSize", "36px", "fontWeight", "800", "color", "#1a1a1a", "textAlign", "center"), 0),
                createElement("text", "Latest thoughts, ideas, and insights from our team.", 
                    Map.of("fontSize", "18px", "color", "#555", "textAlign", "center", "marginTop", "8px"), 1)
            ));
            sections.add(hero);
        }
        
        result.put("sections", sections);
        result.put("settings", settings);
        return result;
    }
    
    private Map<String, Object> createSection(String type) {
        Map<String, Object> section = new HashMap<>();
        section.put("id", "sec-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6));
        section.put("type", type);
        section.put("order", 0);
        section.put("elements", new ArrayList<>());
        return section;
    }
    
    private Map<String, Object> createElement(String type, String content, Map<String, Object> style, int order) {
        Map<String, Object> element = new HashMap<>();
        element.put("id", "elem-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6));
        element.put("type", type);
        element.put("content", content);
        element.put("style", style);
        element.put("order", order);
        element.put("animation", "");
        return element;
    }
}