package com.jocata.promotionsdao;

import com.jocata.promotionsservice.entity.Promotion;

public interface PromotionDao {
    Promotion save(Promotion promotion);

    Promotion findByProductId(Integer productId);
}
