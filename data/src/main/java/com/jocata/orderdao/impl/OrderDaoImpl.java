package com.jocata.orderdao.impl;

import com.jocata.orderdao.OrderDao;
import com.jocata.orderservice.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order findById(Integer id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order update(Order order) {
        return entityManager.merge(order);
    }
}
