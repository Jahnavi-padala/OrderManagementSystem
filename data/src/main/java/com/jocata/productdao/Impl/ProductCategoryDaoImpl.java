package com.jocata.productdao.Impl;

import com.jocata.productdao.ProductCategoryDao;
import com.jocata.productservice.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProductCategoryDaoImpl implements ProductCategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductCategory save(ProductCategory category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public ProductCategory findById(Integer id) {
        return entityManager.find(ProductCategory.class, id);
    }
}
