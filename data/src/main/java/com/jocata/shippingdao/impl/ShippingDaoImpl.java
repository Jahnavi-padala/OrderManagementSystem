package com.jocata.shippingdao.impl;

import com.jocata.shippingdao.ShippingDao;
import com.jocata.shippingservice.entity.Shipping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ShippingDaoImpl implements ShippingDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Shipping save(Shipping shipment) {
        entityManager.persist(shipment);
        return shipment;  // return persisted entity
    }

    @Override
    public Shipping findById(Integer id) {
        return entityManager.find(Shipping.class, id);
    }

    @Override
    public List<Shipping> findByOrderId(Integer orderId) {
        return entityManager.createQuery(
                        "SELECT s FROM Shipment s WHERE s.orderId = :orderId", Shipping.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
