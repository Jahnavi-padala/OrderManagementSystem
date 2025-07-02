package com.jocata.productdao.Impl;

import com.jocata.productdao.ProductDetailDao;
import com.jocata.productservice.entity.ProductDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional

public class ProductDetailDaoImpl implements ProductDetailDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductDetails save(ProductDetails details) {
        entityManager.persist(details);
        return details;
    }

    @Override
    public ProductDetails findById(Integer id) {
        return entityManager.find(ProductDetails.class, id);
    }
    @Override
    public ProductDetails findByProductId(Integer productId) {
        String jpql = "SELECT pd FROM ProductDetails pd WHERE pd.product.id = :productId";
        return entityManager.createQuery(jpql, ProductDetails.class)
                .setParameter("productId", productId)
                .getSingleResult();
    }


}
