package com.jocata.promotionsdao.impl;

import com.jocata.promotionsdao.PromotionDao;
import com.jocata.promotionsservice.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class PromotionsDaoImpl implements PromotionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Promotion save(Promotion promotion) {
        entityManager.persist(promotion);
        return promotion;
    }

    @Override
    public Promotion findByProductId(Integer productId) {
        String jpql = "SELECT p FROM Promotion p WHERE p.productId = :productId";
        try {
            return entityManager.createQuery(jpql, Promotion.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
