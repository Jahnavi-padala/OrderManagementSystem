package com.jocata.productdao.Impl;

import com.jocata.productdao.ProductDao;
import com.jocata.productservice.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public Product findById(Integer id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> search(String keyword) {
        String jpql = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:keyword) OR LOWER(p.category.categoryName) LIKE LOWER(:keyword)";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }
    @Override
    public Product update(Product product) {
        return entityManager.merge(product);
    }
}
