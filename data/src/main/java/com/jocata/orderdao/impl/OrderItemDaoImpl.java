package com.jocata.orderdao.impl;

import com.jocata.orderdao.OrderItemDao;
import com.jocata.orderservice.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional

public class OrderItemDaoImpl implements OrderItemDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderItem save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        String jpql = "SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId";
        TypedQuery<OrderItem> query = entityManager.createQuery(jpql, OrderItem.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
