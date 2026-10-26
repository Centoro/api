package com.centoro.api.controller;

import com.centoro.api.entities.WebsitePage;
import com.centoro.api.repositories.WebsitePageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/website/render")
@CrossOrigin(origins = "*")
@SuppressWarnings("unchecked")
public class WebsitePageRenderController {

    @Autowired
    private WebsitePageRepository websitePageRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/pages/{id}/html")
    public ResponseEntity<?> renderPageHTML(@PathVariable Long id) {
        Optional<WebsitePage> pageOpt = websitePageRepository.findById(id);
        if (pageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePage page = pageOpt.get();
        String html = generatePageHTML(page);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "html", html,
                "title", page.getTitle()
            )
        ));
    }

    @GetMapping("/pages/slug/{slug}/html")
    public ResponseEntity<?> renderPageHTMLBySlug(@PathVariable String slug) {
        WebsitePage page = websitePageRepository.findBySlug(slug);
        if (page == null) {
            return ResponseEntity.notFound().build();
        }
        
        String html = generatePageHTML(page);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "html", html,
                "title", page.getTitle()
            )
        ));
    }

    private String generatePageHTML(WebsitePage page) {
        // Build HTML from page data
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("  <meta charset=\"UTF-8\">\n");
        html.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("  <title>").append(page.getTitle()).append("</title>\n");
        html.append("  <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700;800&display=swap\" rel=\"stylesheet\">\n");
        html.append("  <style>\n");
        html.append("    * { margin:0; padding:0; box-sizing:border-box; }\n");
        html.append("    body { font-family:'Inter',sans-serif; background:#ffffff; color:#1a1a1a; }\n");
        html.append("    .container { max-width:1100px; margin:0 auto; padding:0 20px; }\n");
        html.append("    .section { padding:40px 0; }\n");
        html.append("    .btn { display:inline-block; padding:10px 24px; border-radius:30px; font-weight:600; text-decoration:none; cursor:pointer; border:none; }\n");
        html.append("    .btn-primary { background:#f59e0b; color:#0f172a; }\n");
        html.append("    .btn-primary:hover { background:#d97706; }\n");
        html.append("  </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        
        // Parse and render sections
        try {
            List<Map<String, Object>> sections = objectMapper.readValue(
                page.getSections(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );
            
            for (Map<String, Object> section : sections) {
                Map<String, Object> settings = (Map<String, Object>) section.get("settings");
                String bg = (String) settings.getOrDefault("background", "transparent");
                String textColor = (String) settings.getOrDefault("textColor", "#1a1a1a");
                String padding = (String) settings.getOrDefault("padding", "40px 20px");
                
                html.append("<section style=\"background:").append(bg).append(";color:").append(textColor).append(";padding:").append(padding).append(";\">\n");
                html.append("  <div class=\"container\">\n");
                
                List<Map<String, Object>> elements = (List<Map<String, Object>>) section.get("elements");
                if (elements != null) {
                    for (Map<String, Object> element : elements) {
                        String type = (String) element.get("type");
                        String content = (String) element.get("content");
                        Map<String, Object> style = (Map<String, Object>) element.get("style");
                        String styleStr = style != null ? styleToString(style) : "";
                        
                        switch (type) {
                            case "heading":
                                html.append("    <h1 style=\"").append(styleStr).append("\">").append(content).append("</h1>\n");
                                break;
                            case "text":
                                html.append("    <p style=\"").append(styleStr).append("\">").append(content).append("</p>\n");
                                break;
                            case "button":
                                html.append("    <a href=\"").append(element.getOrDefault("link", "#")).append("\" class=\"btn btn-primary\" style=\"").append(styleStr).append("\">").append(content).append("</a>\n");
                                break;
                            default:
                                html.append("    <div style=\"").append(styleStr).append("\">").append(content).append("</div>\n");
                        }
                    }
                }
                
                html.append("  </div>\n");
                html.append("</section>\n");
            }
        } catch (Exception e) {
            html.append("<p>Error rendering page content.</p>");
        }
        
        html.append("</body>\n");
        html.append("</html>");
        
        return html.toString();
    }
    
    private String styleToString(Map<String, Object> style) {
        if (style == null) return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : style.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }
}