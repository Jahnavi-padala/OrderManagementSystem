package com.jocata.orderdao;

import com.jocata.orderservice.entity.OrderStatus;

import java.util.List;

public interface OrderStatusDao {
    List<OrderStatus> findByOrderId(Integer orderId);
    OrderStatus save(OrderStatus orderStatus);
}
