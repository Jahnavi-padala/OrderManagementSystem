package com.jocata.service;

import com.jocata.promotionsservice.form.PromotionForm;

public interface PromotionService {
    PromotionForm createPromotion(PromotionForm promotionForm);
    PromotionForm getPromotionByProductId(String productId);
}
