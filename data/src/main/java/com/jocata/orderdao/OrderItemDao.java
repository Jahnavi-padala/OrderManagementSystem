package com.jocata.orderdao;

import com.jocata.orderservice.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {
    OrderItem save(OrderItem orderItem);
    List<OrderItem> findByOrderId(Integer orderId);
}
