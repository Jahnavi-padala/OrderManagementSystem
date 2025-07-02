package com.jocata.controller;

import com.jocata.promotionsservice.form.PromotionForm;
import com.jocata.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;


    @PostMapping("/create")
    public ResponseEntity<PromotionForm> createPromotion(@RequestBody PromotionForm promotionForm) {
        PromotionForm createdPromotion = promotionService.createPromotion(promotionForm);
        return ResponseEntity.ok(createdPromotion);
    }

    @GetMapping("/getByProductId/{productId}")
    public ResponseEntity<PromotionForm> getPromotionByProductId(@PathVariable String productId) {
        PromotionForm promotion = promotionService.getPromotionByProductId(productId);
        return ResponseEntity.ok(promotion);
    }
}
