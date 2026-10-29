package com.centoro.api.controller;

import com.centoro.api.dto.DiscountDTO;
import com.centoro.api.entities.Discount;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = "*")
public class DiscountController {

    @Autowired private DiscountService discountService;
    @Autowired private ClientRepository clientRepository;

    @GetMapping
    public ResponseEntity<?> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        List<DiscountDTO> discountDTOs = discounts.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", discountDTOs));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getDiscountsByClient(@PathVariable String clientId) {
        List<Discount> discounts = discountService.getDiscountsByClient(clientId);
        List<DiscountDTO> discountDTOs = discounts.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("success", true, "data", discountDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiscount(@PathVariable Long id) {
        Optional<Discount> discountOpt = discountService.getDiscountById(id);
        if (discountOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(discountOpt.get())));
    }

    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount) {
        Discount saved = discountService.createDiscount(discount);
        return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(saved), "message", "Discount created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        try {
            Discount updated = discountService.updateDiscount(id, discount);
            return ResponseEntity.ok(Map.of("success", true, "data", convertToDTO(updated), "message", "Discount updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        if (!discountService.getDiscountById(id).isPresent()) return ResponseEntity.notFound().build();
        discountService.deleteDiscount(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Discount deleted successfully"));
    }

    private DiscountDTO convertToDTO(Discount discount) {
        DiscountDTO dto = new DiscountDTO();
        dto.setId(discount.getId());
        dto.setDiscountId(discount.getDiscountId());
        dto.setDate(discount.getDate());
        dto.setRef(discount.getRef());
        dto.setClientId(discount.getClientId());
        
        if (discount.getClientId() != null) {
            clientRepository.findById(discount.getClientId()).ifPresent(c -> dto.setClientName(c.getName()));
        }
        
        dto.setDescription(discount.getDescription());
        dto.setDiscountPct(discount.getDiscountPct());
        dto.setDiscountAmount(discount.getDiscountAmount());
        dto.setSource(discount.getSource());
        dto.setCreatedAt(discount.getCreatedAt());
        return dto;
    }
}
