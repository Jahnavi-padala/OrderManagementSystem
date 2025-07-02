package com.jocata.orderdao.impl;

import com.jocata.orderdao.PaymentDao;
import com.jocata.orderservice.entity.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional

public class PaymentDaoImpl implements PaymentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment save(Payment payment) {
        entityManager.persist(payment);
        return payment;
    }

    @Override
    public Payment findByOrderId(Integer orderId) {
        String jpql = "SELECT p FROM Payment p WHERE p.order.id = :orderId";
        TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class);
        query.setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    @Override
    public Payment update(Payment payment) {
        return entityManager.merge(payment);
    }
}
