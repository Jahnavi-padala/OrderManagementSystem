package com.jocata.service.impl;

import com.jocata.productservice.form.ProductDataForm;
import com.jocata.promotionsdao.PromotionDao;
import com.jocata.promotionsservice.entity.Promotion;
import com.jocata.promotionsservice.form.PromotionForm;
import com.jocata.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private RestTemplate restTemplate;
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8083/productservice/api/products";
    @Override
    public PromotionForm createPromotion(PromotionForm promotionForm) {
        // Check product exists
        String productId = promotionForm.getProductId();
        if (!productExists(productId)) {
            throw new RuntimeException("Product ID does not exist: " + productId);
        }

        // Save promotion
        Promotion promotion = new Promotion();
        promotion.setCode(promotionForm.getCode());
        promotion.setProductId(Integer.parseInt(productId));
        promotion.setDiscountPercentage(
                promotionForm.getDiscountPercentage() != null ?
                        new java.math.BigDecimal(promotionForm.getDiscountPercentage()) : null
        );
        promotion.setDiscountAmount(
                promotionForm.getDiscountAmount() != null ?
                        new java.math.BigDecimal(promotionForm.getDiscountAmount()) : null
        );
        promotion.setExpirationDate(LocalDate.parse(promotionForm.getExpirationDate()));

        Promotion saved = promotionDao.save(promotion);

        // Set response
        PromotionForm response = new PromotionForm();
        response.setId(saved.getId().toString());
        response.setCode(saved.getCode());
        response.setProductId(saved.getProductId().toString());
        response.setDiscountPercentage(saved.getDiscountPercentage().toString());
        response.setDiscountAmount(saved.getDiscountAmount().toString());
        response.setExpirationDate(saved.getExpirationDate().toString());
        response.setCreatedAt(saved.getCreatedAt().toString());
        return response;
    }

    @Override
    public PromotionForm getPromotionByProductId(String productId) {
        if (!productExists(productId)) {
            throw new RuntimeException("Product ID does not exist: " + productId);
        }

        Promotion promotion = promotionDao.findByProductId(Integer.parseInt(productId));
        if (promotion == null) {
            throw new RuntimeException("No promotion found for product ID: " + productId);
        }

        PromotionForm form = new PromotionForm();
        form.setId(promotion.getId().toString());
        form.setCode(promotion.getCode());
        form.setProductId(promotion.getProductId().toString());
        form.setDiscountPercentage(promotion.getDiscountPercentage().toString());
        form.setDiscountAmount(promotion.getDiscountAmount().toString());
        form.setExpirationDate(promotion.getExpirationDate().toString());
        form.setCreatedAt(String.valueOf(promotion.getCreatedAt()));
        return form;
    }

    private boolean productExists(String productId) {
        try {
            ProductDataForm productDataForm = restTemplate.getForObject(
                    PRODUCT_SERVICE_URL + "/getProductById/" + productId,
                    ProductDataForm.class
            );
            return productDataForm != null && productDataForm.getProduct() != null;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

}
