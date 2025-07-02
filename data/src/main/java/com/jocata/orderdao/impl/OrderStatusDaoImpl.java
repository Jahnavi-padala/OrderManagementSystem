package com.jocata.orderdao.impl;

import com.jocata.orderdao.OrderStatusDao;
import com.jocata.orderservice.entity.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;

@Repository
@Transactional

public class OrderStatusDaoImpl implements OrderStatusDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        entityManager.persist(orderStatus);
        return orderStatus;
    }

    @Override
    public List<OrderStatus> findByOrderId(Integer orderId) {
        String jpql = "SELECT os FROM OrderStatus os WHERE os.order.id = :orderId";
        TypedQuery<OrderStatus> query = entityManager.createQuery(jpql, OrderStatus.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
