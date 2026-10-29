package com.centoro.api.service;

import com.centoro.api.entities.Discount;
import com.centoro.api.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
    
    @Autowired
    private DiscountRepository discountRepository;
    
    @Transactional
    public Discount createDiscount(Discount discount) {
        discount.setDiscountId("DSC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        discount.setCreatedAt(new Date());
        if (discount.getDiscountPct() == null) discount.setDiscountPct(0.0);
        if (discount.getDiscountAmount() == null) discount.setDiscountAmount(0.0);
        if (discount.getSource() == null) discount.setSource("quote");
        return discountRepository.save(discount);
    }
    
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }
    
    // FIX: Changed from Long to String
    public List<Discount> getDiscountsByClient(String clientId) {
        return discountRepository.findByClientId(clientId);
    }
    
    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }
    
    @Transactional
    public Discount updateDiscount(Long id, Discount discount) {
        Discount existing = discountRepository.findById(id).orElseThrow();
        existing.setDate(discount.getDate());
        existing.setRef(discount.getRef());
        existing.setClientId(discount.getClientId());
        existing.setDescription(discount.getDescription());
        existing.setDiscountPct(discount.getDiscountPct());
        existing.setDiscountAmount(discount.getDiscountAmount());
        existing.setSource(discount.getSource());
        return discountRepository.save(existing);
    }
    
    @Transactional
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
