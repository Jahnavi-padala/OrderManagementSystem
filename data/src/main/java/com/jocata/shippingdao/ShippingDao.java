package com.jocata.shippingdao;

import com.jocata.shippingservice.entity.Shipping;

import java.util.List;

public interface ShippingDao {
    Shipping save(Shipping shipment);
    Shipping findById(Integer id);
    List<Shipping> findByOrderId(Integer orderId);
}
